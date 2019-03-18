package io.zipcoder;

import io.zipcoder.utils.Item;
import io.zipcoder.utils.ItemParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {
    public List<Item> parseItemList(String valueToParse) {
        return null;
    }

    public Item parseSingleItem(String singleItem) throws ItemParseException {
        List<String> matched = new ArrayList<>();

        singleItem = singleItem.toLowerCase();

        Pattern pattern = Pattern.compile("[:|@|^|%](.*?)[;|##]");
        Matcher matcher = pattern.matcher(singleItem);

        while(matcher.find()) {
            matched.add(matcher.group(1));
        }
        
        String name = matched.get(0);
        double price = Double.parseDouble(matched.get(1));
        String type = matched.get(2);
        String expiration = matched.get(3);

        return new Item(name, price, type, expiration);
    }
}
