import java.util.ArrayList;

public class BookCurrencyConverter {
    private static Double USD = 3.8;
    private static Double EUR = 4.3;

    public static ArrayList<Book> convertBookCatalogCurrency(ArrayList<Book> bookCatalog, String currency){

        if(currency == null){
            return bookCatalog;
        }

        ArrayList<Book> bookList = new ArrayList<>();

        if(currency.equals("original")) {
            for (Book book : bookCatalog) {
                double oldPrice = book.getPrice();
                String origCurrency = book.getOriginalCurrency();
                String curr = book.getCurrency();
                if (origCurrency.equals(curr)){
                    bookList.add(book);
                }else {
                    if(origCurrency.equals("USD")){
                        double newPrice = oldPrice / USD;
                        book.setPrice(Math.floor(newPrice));
                        book.setCurrency("USD");
                        bookList.add(book);
                    }else if (origCurrency.equals("EUR")){
                        double newPrice = oldPrice / EUR;
                        book.setPrice(Math.floor(newPrice));
                        book.setCurrency("EUR");
                        bookList.add(book);
                    }
                }
            }
        }else if (currency.equals("PLN")){
            for (Book book : bookCatalog) {
                double oldPrice = book.getPrice();
                String origCurrency = book.getOriginalCurrency();
                String curr = book.getCurrency();
                if (!origCurrency.equals(curr) || origCurrency.equals("PLN")){
                    bookList.add(book);
                }else {
                    if(origCurrency.equals("USD")){
                        double newPrice = oldPrice * USD;
                        book.setPrice(Math.floor(newPrice));
                        book.setCurrency("PLN");
                        bookList.add(book);
                    }else if (origCurrency.equals("EUR")){
                        double newPrice = oldPrice * EUR;
                        book.setPrice(Math.floor(newPrice));
                        book.setCurrency("PLN");
                        bookList.add(book);
                    }
                }
            }
        }
        return bookList;
    }
}