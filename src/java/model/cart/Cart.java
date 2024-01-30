/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.cart;

import model.item.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author datng
 */
public class Cart {
    private List<Item> items = new ArrayList<>();

    public Cart() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getQuantity(int id, int size) {
        return getItem(id, size).getQuantity();
    }

    private Item getItem(int id, int size) {
        for (Item item : items) {
            if (item.getProduct().getId() == id && item.getSize() == size) return item;
        }
        return null;
    }

    public void addItem(Item t) {
        if (getItem(t.getProduct().getId(), t.getSize()) != null) {
            Item m = getItem(t.getProduct().getId(), t.getSize());
            m.setQuantity(m.getQuantity() + t.getQuantity());
        } else {
            items.add(t);
        }
    }

    public void removeItem(int id, int size) {
        if (getItem(id, size) != null) {
            items.remove(getItem(id, size));
        }
    }

    public double getTotalMoney() {
        double t = 0;
        for (Item item : items) {
            t += (item.getQuantity() * item.getProduct().getOutPrice());
        }
        return t;
    }
}
