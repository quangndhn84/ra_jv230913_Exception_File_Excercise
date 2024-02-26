package ra.run;

import ra.imp.Book;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class BookRun {
    public static List<Book> listBooks;

    public static void main(String[] args) {
        readDataFromFile();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("***************MENU****************");
            System.out.println("1. Nhập thông tin sách");
            System.out.println("2. Hiển thị thông tin sách");
            System.out.println("3. Cập nhật thông tin sách theo mã sách");
            System.out.println("4. Xóa sách theo mã sách");
            System.out.println("5. Sắp xếp sách theo giá bán tăng dần");
            System.out.println("6. Thống kê sách theo khoảng giá");
            System.out.println("7. Tìm kiếm sách theo tên tác giả");
            System.out.println("8. Thoát");
            System.out.print("Lựa chọn của bạn:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    inputListData(scanner);
                    break;
                case 2:
                    displayListData();
                    break;
                case 3:
                    updateBook(scanner);
                    break;
                case 4:
                    deleteBook(scanner);
                    break;
                case 5:
                    sortBookByExportASC();
                    break;
                case 6:
                    staticticBookByPrice(scanner);
                    break;
                case 7:
                    findBookByAuthor(scanner);
                    break;
                case 8:
                    writeDataToFile();
                    System.exit(0);
                default:
                    System.err.println("Vui lòng nhập từ 1-8");
            }
        } while (true);
    }

    public static void readDataFromFile() {
        File file = new File("books.txt");
        if (file.exists()) {
            FileInputStream fis;
            ObjectInputStream ois;
            try {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                listBooks = (List<Book>) ois.readObject();
                ois.close();
                fis.close();
            } catch (Exception ex) {
                System.err.println("Có lỗi trong quá trình đọc file");
            }
        } else {
            listBooks = new ArrayList<>();
        }
    }

    public static void writeDataToFile() {
        File file = new File("books.txt");
        FileOutputStream fos;
        ObjectOutputStream oos;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(listBooks);
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception ex) {
            System.err.println("Có lỗi trong quá trình ghi file");
        }
    }

    public static void inputListData(Scanner scanner) {
        System.out.println("Nhập vào số sách cần nhập thông tin:");
        int numberOfBooks = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfBooks; i++) {
            Book bookNew = new Book();
            bookNew.inputData(scanner);
            listBooks.add(bookNew);
        }
    }

    public static void displayListData() {
        listBooks.forEach(book -> book.displayData());
    }

    public static void updateBook(Scanner scanner) {
        System.out.println("Nhập vào mã sách cần cập nhật:");
        int bookIdUpdate = Integer.parseInt(scanner.nextLine());
        int indexUpdate = findIndexById(bookIdUpdate);
        if (indexUpdate >= 0) {
            do {
                System.out.println("1. Cập nhật tên sách");
                System.out.println("2. Cập nhật giá nhập");
                System.out.println("3. Cập nhật giá bán");
                System.out.println("4. Cập nhật tác giả");
                System.out.println("5. Cập nhật ngày tạo");
                System.out.println("6. Cập nhật mô tả");
                System.out.print("Lựa chọn của bạn:");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Nhập vào tên sách cập nhật:");
                        listBooks.get(indexUpdate).setBookName(scanner.nextLine());
                        break;
                    case 2:
                        System.out.println("Nhập vào giá nhập cập nhật:");
                        listBooks.get(indexUpdate).setImportPrice(Float.parseFloat(scanner.nextLine()));
                        break;
                    case 3:
                        System.out.println("Nhập vào giá bạn cập nhật:");
                        listBooks.get(indexUpdate).setExportPrice(Float.parseFloat(scanner.nextLine()));
                        break;
                    case 4:
                        System.out.println("Nhập vào tác giả cập nhật: ");
                        listBooks.get(indexUpdate).setAuthor(scanner.nextLine());
                        break;
                    case 5:
                        System.out.println("Nhâp vào ngày tạo cập nhật:");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            listBooks.get(indexUpdate).setCreated(sdf.parse(scanner.nextLine()));
                        } catch (Exception ex) {
                            System.err.println("Định dạng ngày không đúng dd/MM/yyyy");
                        }
                        break;
                    case 6:
                        System.out.println("Nhập vào mô tả cập nhật:");
                        listBooks.get(indexUpdate).setDescriptions(scanner.nextLine());
                        break;
                    default:
                        System.err.println("Vui lòng chọn từ 1-6");
                }
            } while (true);
        } else {
            System.err.println("Mã sách không tồn tại");
        }

    }

    public static int findIndexById(int bookId) {
        for (int i = 0; i < listBooks.size(); i++) {
            if (listBooks.get(i).getBookId() == bookId) {
                return i;
            }
        }
        return -1;
    }

    public static void deleteBook(Scanner scanner) {
        System.out.println("Nhập vào mã sách cần xóa:");
        int bookIdDelete = Integer.parseInt(scanner.nextLine());
        int indexDelete = findIndexById(bookIdDelete);
        if (indexDelete >= 0) {
            listBooks.remove(indexDelete);
        } else {
            System.err.println("Mã sách không tồn tại");
        }
    }

    public static void sortBookByExportASC() {
        Collections.sort(listBooks, new Comparator<Book>() {
            @Override
            public int compare(Book book, Book t1) {
                return (int) Math.ceil(book.getExportPrice() - t1.getExportPrice());
            }
        });
    }

    public static void staticticBookByPrice(Scanner scanner) {
        System.out.println("Nhập vào khoảng giá với giá bán từ:");
        float exportPriceFrom = Float.parseFloat(scanner.nextLine());
        System.out.println("Nhập vào khoảng giá với giá bán đến:");
        float exportPriceTo = Float.parseFloat(scanner.nextLine());
        if (exportPriceTo < exportPriceFrom) {
            System.out.printf("Có %d sách trong khoảng giá %f đến %f\n", 0, exportPriceFrom, exportPriceTo);
        } else {
            int cntBook = 0;
            for (Book book : listBooks) {
                if (book.getExportPrice() >= exportPriceFrom && book.getExportPrice() <= exportPriceTo) {
                    cntBook++;
                }
            }
            System.out.printf("Có %d sách trong khoảng giá %f đến %f\n", cntBook, exportPriceFrom, exportPriceTo);
        }
    }

    public static void findBookByAuthor(Scanner scanner) {
        System.out.println("Nhập vào tên tác giả của sách:");
        String authorSearch = scanner.nextLine();
        listBooks.forEach(book -> {
            if (book.getAuthor().toLowerCase().contains(authorSearch.toLowerCase())) {
                book.displayData();
            }
        });
    }
}
