package NM;


public class GaussianElimination {

    // Функция для решения системы линейных уравнений методом Гаусса
    public static int gaussianElimination(double[][] matrixA, double[] vectorB, double[] X) {
        int countRowMatrix = matrixA.length;

        int ks = 0; // Признак правильности решения

        double max;
        int maxRow;
        // Прямой ход метода Гаусса
        for (int k = 0; k < countRowMatrix; k++) {
            // Поиск максимального элемента в столбце
            max = Math.abs(matrixA[k][k]);
            maxRow = k;
            for (int j = k + 1; j < countRowMatrix; j++) {
                if (Math.abs(matrixA[j][k]) > max) {
                    max = Math.abs(matrixA[j][k]);
                    maxRow = j;
                }
            }

            double[] temp;
            // Перестановка строк
            if (maxRow != k) {
                temp = matrixA[k];
                matrixA[k] = matrixA[maxRow];
                matrixA[maxRow] = temp;

                double tempB = vectorB[k];
                vectorB[k] = vectorB[maxRow];
                vectorB[maxRow] = tempB;
            }

            // Проверка на нулевой элемент на диагонали
            if (matrixA[k][k] == 0) {
                ks = 1; // Система не имеет решений или имеет бесконечно много решений
                return ks;
            }

            // Прямой ход
            for (int i = k + 1; i < countRowMatrix; i++) {
                double factor = matrixA[i][k] / matrixA[k][k];
                vectorB[i] -= factor * vectorB[k];
                for (int j = k; j < countRowMatrix; j++) {
                    matrixA[i][j] -= factor * matrixA[k][j];
                }
            }
        }

        // Обратный ход для нахождения решения
        for (int i = countRowMatrix - 1; i >= 0; i--) {
            double sum = vectorB[i];
            for (int j = i + 1; j < countRowMatrix; j++) {
                sum -= matrixA[i][j] * X[j];
            }
            X[i] = sum / matrixA[i][i];
        }

        ks = 0; // Решение успешно найдено
        return ks;
    }

    public static void forwardMethod(double[][] matrixA, double[] vectorB) {
        double buf;
        double[] bufVector;
        for (int i = 0; i < matrixA.length; i++) {
            matrixA[i] = div(matrixA[i], matrixA[i][i]);
            vectorB[i] = vectorB[i] / matrixA[i][i];
            for (int j = i+1; j < matrixA.length; j++){
                bufVector = mul(matrixA[i], matrixA[j][i]);
                buf = vectorB[i] * matrixA[j][i];
                matrixA[j] = sub(matrixA[j], bufVector);
                vectorB[j] -= buf;
            }
        }
        System.out.println("Результат прямого хода");
        printMatrix(matrixA,vectorB);
        System.out.println("Обратный ход");
        reversalMethod(matrixA, vectorB);
    }

    public static void reversalMethod(double[][] matrixA, double[] vectorB){
        double[] result = new double[matrixA.length];
        double temp;
        for(int i = matrixA.length - 1; i >= 0; i--) {
            temp = 0;
            for(int j = i+1; j < matrixA.length; j++){
                temp += matrixA[i][j] * result[j];

            }
            result[i] = vectorB[i] - temp;
        }
        for (int i=0; i < result.length; i++){
            System.out.printf("X[%d] = %.4f\n", i + 1, result[i]);
        }
    }


    public static double[] mul(double[] vector, double value){
        double[] result = new double[vector.length];

        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] * value;
        }
        return result;
    }

    public static double[] div(double[] vector, double value) {
        double[] result = new double[vector.length];

        for (int i = 0; i < vector.length; i++) {
            result[i] = vector[i] / value;
        }
        return result;
    }

    public static double[] sub(double[] minuend, double[] subtrahend){
        double[] result = new double[minuend.length];
        for (int i = 0; i < minuend.length; i++){
            result[i] = minuend[i] - subtrahend[i];
        }
        return result;
    }

    public static void printMatrix(double[][] matrix, double[] vector){
        System.out.println("Матрица: ");
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length; j++){
                System.out.printf("%.4f\t", matrix[i][j]);
            }
            System.out.println("| " + vector[i]);
        }
    }

    public static void main(String[] args) {
        // Входные параметры
        int Nn = 3; // Размерность системы

        double[][] matrixA = {
                {3.19, 2.89, 2.47},
                {4.43, 4.02, 3.53},
                {3.40, 2.92, 2.40}
        };

        double[] vectorB = {33.91, 47.21, 32.92};
        forwardMethod(matrixA, vectorB);

        double[] X = new double[Nn]; // Массив для хранения решения

        // Вызов метода для решения системы
//        int ks = gaussianElimination(matrixA, vectorB, X);
//
//        // Проверка кода ошибки и вывод результата
//        if (ks == 0) {
//            System.out.println("Решение системы:");
//            for (int i = 0; i < Nn; i++) {
//                System.out.printf("X[%d] = %.4f\n", i + 1, X[i]);
//            }
//        } else {
//            System.out.println("Система не имеет решения или имеет бесконечно много решений.");
//        }
    }
}

