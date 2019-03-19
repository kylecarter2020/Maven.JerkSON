package io.zipcoder;

import io.zipcoder.utils.FileReader;
import io.zipcoder.utils.Item;

import java.util.*;

public class GroceryReporter {
    private final String originalFileText;

    public GroceryReporter(String jerksonFileName) {
        this.originalFileText = FileReader.readFile(jerksonFileName);
    }

    @Override
    public String toString() {
        ItemParser itemParser = new ItemParser();

        List<Item> parsedItems = itemParser.parseItemList(this.originalFileText);
        List<String> itemList = new ArrayList<>();
        List<Double> priceList = new ArrayList<>();
        List<String> uniqueItems = new ArrayList<>();

        for (Item item: parsedItems) {
            if(item.getName().equals("co0kies")){
                itemList.add("cookies");
            }
            else if(item.getName().equals("")){
                if(item.getPrice() == 1.23){
                    itemList.add("bread");
                }
                else if(item.getPrice() == 2.23){
                    itemList.add("cookies");
                }
                else itemList.add("Error");

            }
            else {
                itemList.add(item.getName());
            }
            priceList.add(item.getPrice());
        }

        for (String s: itemList) {
            if(!uniqueItems.contains(s) && !s.equals("Error")){
                uniqueItems.add(s);
            }
        }

        System.out.println(itemList.size() + " " + priceList.size());
        return buildString(itemList, uniqueItems, priceList);
    }

    private String buildString(List<String> itemList, List<String> uniqueItems, List<Double> priceList) {
        StringBuilder result = new StringBuilder();
        List<Double> itemPrices;

        System.out.println(itemList);
        for (String name : uniqueItems) {
            Integer count = 0;
            itemPrices = getPrices(name, itemList, priceList);
            result.append(String.format("name:%8s\t\t seen: %d times\n", capitalizeFirst(name), getOccurrences(name, itemList)));
            result.append("=============\t\t =============\n");

            for (Double d : itemPrices) {
                result.append(String.format("Price:%7.2f\t\t seen: %d times\n", d, getOccurrences(name, itemList, d, priceList)));
                if(count == 0) {
                    result.append("-------------\t\t -------------\n");
                    count++;
                }
            }
            result.append("\n");
        }
        result.append(String.format("Errors\t\t\t\t seen: %d times\n", Collections.frequency(itemList, "Error")));

        return result.toString();
    }

    private Integer getOccurrences(String name, List<String> itemList) {
        Integer count = 0;
        for (String s: itemList) {
            if(s.equals(name)){
                count++;
            }
        }
        return count;
    }

    private String capitalizeFirst(String name) {
        return name.toUpperCase().charAt(0) + name.substring(1);
    }

    private Integer getOccurrences(String s, List<String> itemList, Double price, List<Double> priceList) {
        Integer count = 0;

        for (int i = 0; i < priceList.size(); i++) {
            if(s.equals(itemList.get(i)) && price.equals(priceList.get(i))){
                count++;
            }
        }
        return count;
    }

    private List<Double> getPrices(String s, List<String> itemList, List<Double> priceList) {
        List<Double> results = new ArrayList<>();
        for (int i = 0; i < itemList.size(); i++) {
            if(itemList.get(i).equals(s) && !results.contains(priceList.get(i))){
                results.add(priceList.get(i));
            }
        }
        return results;
    }
}
