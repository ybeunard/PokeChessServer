package com.pokechess.server.controllers.pokemon.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class PokemonPageDTO {
    private List<PokemonDTO> items;
    private int currentPage;
    private int size;
    private int lastPage;
    private long total;

    public List<PokemonDTO> getItems() {
        return items;
    }

    public void setItems(List<PokemonDTO> items) {
        this.items = items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonPageDTO && EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(
                "PokemonPageDTO [items=%s, currentPage=%s, lastPage=%s, size=%s, total=%s]", this.items, this.currentPage, this.lastPage, this.size, this.total);
    }
}
