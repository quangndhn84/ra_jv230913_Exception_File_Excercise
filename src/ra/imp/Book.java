package ra.imp;

import ra.IBook;
import ra.run.BookRun;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Book implements IBook, Serializable {
    private int bookId;
    private String bookName;
    private float importPrice;
    private float exportPrice;
    private String author;
    private Date created;
    private String descriptions;

    public Book() {
    }

    public Book(int bookId, String bookName, float importPrice, float exportPrice, String author, Date created, String descriptions) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.author = author;
        this.created = created;
        this.descriptions = descriptions;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(float importPrice) {
        this.importPrice = importPrice;
    }

    public float getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(float exportPrice) {
        this.exportPrice = exportPrice;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public void inputData(Scanner scanner) {
        this.bookId = generateBookId();
        this.bookName = inputBookName(scanner);
        this.importPrice = inputImportPrice(scanner);
        this.exportPrice = inputExportPrice(scanner);
        this.author = inputAuthor(scanner);
        this.created = inputCreated(scanner);
        this.descriptions = inputDescription(scanner);
    }

    public int generateBookId() {
        int maxId;
        if (BookRun.listBooks.size() == 0) {
            maxId = 0;
        } else {
            maxId = BookRun.listBooks.get(0).getBookId();
            for (int i = 1; i < BookRun.listBooks.size(); i++) {
                if (maxId < BookRun.listBooks.get(i).getBookId()) {
                    maxId = BookRun.listBooks.get(i).getBookId();
                }
            }
        }
        return maxId + 1;
    }

    public String inputBookName(Scanner scanner) {
        System.out.println("Nhập vào tên sách:");
        do {
            String bookNameInp = scanner.nextLine();
            boolean isExist = false;
            for (Book book : BookRun.listBooks) {
                if (book.bookName.equals(bookNameInp)) {
                    isExist = true;
                    break;
                }
            }
            if (isExist) {
                System.err.println("Tên sách đã tồn tại, vui lòng nhập lại");
            } else {
                if (Pattern.matches("B...", bookNameInp)) {
                    return bookNameInp;
                } else {
                    System.err.println("Tên sách phải có 4 ký tự, bắt đầu là B, vui lòng nhập lại");
                }
            }
        } while (true);
    }

    public float inputImportPrice(Scanner scanner) {
        System.out.println("Nhập vào giá nhập của sách:");
        do {
            try {
                float importPriceInp = Float.parseFloat(scanner.nextLine());
                if (importPriceInp > 0) {
                    return importPriceInp;
                } else {
                    System.err.println("Giá nhập phải có giá trị lớn hơn 0, vui lòng nhập lại");
                }
            } catch (NumberFormatException ex) {
                System.err.println("Giá nhập phải là số thực, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println("Có lỗi trong quá trình xử lý, vui lòng nhập lại");
            }
        } while (true);
    }

    public float inputExportPrice(Scanner scanner) {
        System.out.println("Nhập vào giá xuất của sách:");
        do {
            try {
                float exportPriceInp = Float.parseFloat(scanner.nextLine());
                if (exportPriceInp > this.importPrice) {
                    return exportPriceInp;
                } else {
                    System.err.println("Giá xuất phải có giá trị lớn hơn giá nhập, vui lòng nhập lại");
                }
            } catch (NumberFormatException ex) {
                System.err.println("Giá xuất phải là số thực, vui lòng nhập lại");
            } catch (Exception ex) {
                System.err.println("Có lỗi trong quá trình xử lý, vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputAuthor(Scanner scanner) {
        System.out.println("Nhập vào tác giả sách:");
        do {
            String authorInp = scanner.nextLine();
            if (authorInp.length() >= 6 && authorInp.length() <= 50) {
                return authorInp;
            } else {
                System.err.println("Tên tác giả có từ 6 đến 50 ký tự, vui lòng nhập lại");
            }
        } while (true);
    }

    public Date inputCreated(Scanner scanner) {
        System.out.println("Nhập vào ngày tạo sách:");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        do {
            try {
                Date createdInp = sdf.parse(scanner.nextLine());
                return createdInp;
            } catch (Exception ex) {
                System.err.println("Ngày tạo có định dạng dd/MM/yyyy, vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputDescription(Scanner scanner) {
        System.out.println("Nhập vào mô tả sách:");
        do {
            String descriptionInp = scanner.nextLine();
            if (descriptionInp.length() <= 500) {
                return descriptionInp;
            } else {
                System.err.println("Mô tả sách có độ dài tối đa 500 ký tự, vui lòng nhập lại");
            }
        } while (true);
    }

    @Override
    public void displayData() {
        System.out.printf("Mã sách: %d - Tên sách: %s - Giá nhập: %f - Giá xuất: %f\n",
                this.bookId, this.bookName, this.importPrice, this.exportPrice);
        System.out.printf("Tác giả: %s - Ngày tạo: %s - Mô tả: %s\n",
                this.author, this.created, this.descriptions);
    }
}
