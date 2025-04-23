package ru.vensy.comfortsofttest.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import ru.vensy.comfortsofttest.exception.FileReadException;
import ru.vensy.comfortsofttest.exception.NotFoundException;
import ru.vensy.comfortsofttest.service.FindService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FindServiceImpl implements FindService {
    @Override
    public int findMin(int n, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new NotFoundException("Файл не найден.");
        }
        if (!file.getName().endsWith(".xlsx")) {
            throw new IllegalArgumentException("Некорректный формат файла, ожидается файл в формате xlsx.");
        }

        int[] numbers = extractNumbersFromFile(file);
        if (numbers.length < n) {
            throw new IllegalArgumentException("Переданное число превышает количество элементов в столбце.");
        }

        int minNumber = findMinNumber(numbers, n);
        log.info("Found {} min number: {}", n, minNumber);

        return minNumber;
    }

    private int[] extractNumbersFromFile(File file) {
        List<Integer> numbers = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(fis);

            Sheet sheet = workbook.getSheetAt(0);

            int columnIndex = findColumnIndex(sheet);
            if (columnIndex < 0) {
                throw new NotFoundException("В файле отсутствует столбец с данными.");
            }

            List<String> incorrectFormatCells = new ArrayList<>();

            for (Row row : sheet) {
                Cell cell = row.getCell(columnIndex);
                if (cell == null || cell.getCellType() == CellType.BLANK) continue;

                int number;
                if (cell.getCellType() == CellType.NUMERIC) {
                    number = (int) cell.getNumericCellValue();
                    numbers.add(number);
                } else if (cell.getCellType() == CellType.STRING) {
                    try {
                        number = Integer.parseInt(cell.getStringCellValue());
                        numbers.add(number);
                    } catch (NumberFormatException ex) {
                        int rowNum = cell.getRowIndex() + 1;
                        incorrectFormatCells.add(CellReference.convertNumToColString(columnIndex) + rowNum);
                    }
                } else {
                    int rowIndex = cell.getRowIndex() + 1;
                    incorrectFormatCells.add(CellReference.convertNumToColString(columnIndex) + rowIndex);
                }
            }

            if (!incorrectFormatCells.isEmpty()) {
                throw new IllegalArgumentException("Следующие ячейки содержат данные в некорректном формате: " +
                        String.join(", ", incorrectFormatCells));
            }
        } catch (IOException ex) {
            throw new FileReadException("Ошибка чтения файла: " + ex.getMessage());
        }

        return numbers.stream().mapToInt(i -> i).toArray();
    }

    private int findColumnIndex(Sheet sheet) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    return cell.getColumnIndex();
                }
            }
        }

        return -1;
    }

    private int findMinNumber(int[] numbers, int n) {
        return findMinNumber(numbers, 0, numbers.length - 1, n - 1);
    }

    private int findMinNumber(int[] numbers, int left, int right, int n) {
        if (left == right) return numbers[left];

        int partitionIndex = partition(numbers, left, right);

        if (n == partitionIndex) {
            return numbers[n];
        } else if (n < partitionIndex) {
            return findMinNumber(numbers, left, partitionIndex - 1, n);
        } else {
            return findMinNumber(numbers, partitionIndex + 1, right, n);
        }
    }

    private int partition(int[] numbers, int left, int right) {
        int pivot = numbers[right];
        int index = left;

        for (int i = left; i <= right ; i++) {
            if (numbers[i] < pivot) {
                swap(numbers, i, index);
                index++;
            }
        }
        swap(numbers, right, index);

        return index;
    }

    private void swap(int[] numbers, int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }
}
