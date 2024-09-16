package NM;

public class CholeskyDecomposition {

    // Функция для умножения матрицы на ее транспонированную
    public static double[][] multiplyWithTranspose(double[][] A) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double sum = 0.0;
                for (int k = 0; k < n; k++) {
                    sum += A[k][i] * A[k][j];
                }
                C[i][j] = sum;
            }
        }
        return C;
    }

    // Функция для умножения транспонированной матрицы на вектор
    public static double[] multiplyWithTransposeVector(double[][] A, double[] B) {
        int n = A.length;
        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            double sum = 0.0;
            for (int j = 0; j < n; j++) {
                sum += A[j][i] * B[j];
            }
            y[i] = sum;
        }
        return y;
    }

    // Метод Холецкого для решения симметричной системы
    public static int choleskyDecomposition(double[][] A, double[] B, double[] X) {
        int n = A.length;
        double[][] L = new double[n][n];
        int ks = 0; // Признак правильности решения

        // Разложение Холецкого
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                double sum = 0;

                // Диагональные элементы
                if (i == j) {
                    for (int k = 0; k < j; k++) {
                        sum += L[j][k] * L[j][k];
                    }
                    L[j][j] = Math.sqrt(A[j][j] - sum);
                } else {
                    // Вне диагонали
                    for (int k = 0; k < j; k++) {
                        sum += L[i][k] * L[j][k];
                    }
                    L[i][j] = (A[i][j] - sum) / L[j][j];
                }
            }

            // Проверка на положительность диагональных элементов
            if (L[i][i] <= 0) {
                ks = 1; // Матрица не является положительно определенной
                return ks;
            }
        }

        // Решение системы L * Y = B
        double[] Y = new double[n];
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < i; j++) {
                sum += L[i][j] * Y[j];
            }
            Y[i] = (B[i] - sum) / L[i][i];
        }

        // Решение системы L^T * X = Y
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += L[j][i] * X[j];
            }
            X[i] = (Y[i] - sum) / L[i][i];
        }

        ks = 0; // Решение успешно найдено
        return ks;
    }

    public static void main(String[] args) {
        // Входные параметры
        int Nn = 3; // Размерность системы

        // Исходная несимметричная матрица
        double[][] Aa = {
                {3.19, 2.89, 2.47},
                {4.43, 4.02, 3.53},
                {3.40, 2.92, 2.40}
        };

        // Исходный вектор правой части
        double[] Bb = {33.91, 47.21, 32.92};

        // Шаг 1: Умножаем матрицу на её транспонированную для симметризации
        double[][] symA = multiplyWithTranspose(Aa);

        // Шаг 2: Преобразуем вектор правых частей в соответствии с новой симметричной матрицей
        double[] y = multiplyWithTransposeVector(Aa, Bb);

        // Массив для хранения решения
        double[] X = new double[Nn];

        // Шаг 3: Решаем систему методом Холецкого
        int ks = choleskyDecomposition(symA, y, X);

        // Проверка кода ошибки и вывод результата
        if (ks == 0) {
            System.out.println("Решение системы:");
            for (int i = 0; i < Nn; i++) {
                System.out.printf("X[%d] = %.4f\n", i + 1, X[i]);
            }
        } else {
            System.out.println("Матрица не является положительно определенной.");
        }
    }
}
