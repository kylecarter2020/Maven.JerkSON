package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {
    public List<Item> parseItemList(String valueToParse) {
        valueToParse = valueToParse.toLowerCase();
        List<String> singleItem = new ArrayList<>();
        List<Item> parsedItem = new ArrayList<>();

        Pattern pattern = Pattern.compile("name(.*?)##");
        Matcher matcher = pattern.matcher(valueToParse);

        while(matcher.find()) {
            singleItem.add(matcher.group());
        }
        System.out.println(singleItem +"\n" + singleItem.size());
        for (String s: singleItem) {
            try {
                parsedItem.add(parseSingleItem(s));
            } catch (ItemParseException e) {
                parsedItem.add(new Item("Error", null, null, null));
            }
        }
        return parsedItem;
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        List<String> matched = new ArrayList<>();

        singleItem = singleItem.toLowerCase();

        Pattern pattern = Pattern.compile("[:|@|^|!|*|%](.*?)[;|##]");
        Matcher matcher = pattern.matcher(singleItem);

        while(matcher.find()) {
            matched.add(matcher.group(1));
        }
        if(matched.size() == 4) {
            double price;
            String name = matched.get(0);
            if(matched.get(1).equals("")) {
                price = 1.23;
            }
            else {
                price = Double.parseDouble(matched.get(1));
            }
            String type = matched.get(2);
            String expiration = matched.get(3);
            return new Item(name, price, type, expiration);
        }
        throw new ItemParseException();
    }
}
