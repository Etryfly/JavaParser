import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




public class Parser {

    public static Document getDocument(String url) throws IOException  {


        return Jsoup.connect(url).timeout(10 * 10000).get();
    }

    public static void main(String[] args) throws IOException {

        ArrayList<Item> items = new ArrayList<>();



        for (int i = 1; i <= 15; i++) {
            String url = "http://www.eldorado.ru/cat/1482093/page/" + i + "/?sort=price&type=desc"; // урл странички

            Document doc = getDocument(url);

            Elements imgs = doc.select(".itemPicture .link img");

            for (Element img : imgs) {
                Item item = new Item();
                item.setDescription(img.attr("title"));
                item.setImage(img.attr("src"));
                items.add(item);

            }

            Elements itemPrice = doc.select(".itemPrice");
            int index = 0;
            for (Element price : itemPrice) {
                Item item = items.get(index);
                item.setPrice(price.text());
                items.set(index, item);

                index++;
            }

        }

        //items = parse(items);

        printItemsFields(items);





    }


    public static ArrayList<Item> parse(ArrayList<Item> items) { //удаляет пустые элементы
        for (int i = 0; i < items.size(); i ++) {
            Item item = items.get(i);
            if (item.getPrice() == null) continue;
            if (item.getDescription().equals(" ") || item.getImage().equals(" ") || item.getPrice().equals(" ") ) {
                items.remove(i);
            }
        }

        return items;
    }

    public static void printItemsFields(ArrayList<Item> items) {
        for (Item item : items) {
            System.out.println("DESK: " + item.getDescription());
            System.out.println("IMAGE: " + item.getImage());
            System.out.println("PRICE: " + item.getPrice());
            System.out.println(" ");
        }

    }




    public static class Item {
        public String image;
        public String description;
        public String price;

        public String getDescription() {
            return description;
        }

        public String getPrice() {
            return price;
        }



        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}






























