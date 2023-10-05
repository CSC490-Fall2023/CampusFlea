package com.example.controllers;
import java.util.list;
import.com.example.listings.ListingService;
public class ListingController {
    private ListingService listingService;
    public ListingController() {
        this.listingService = new ListingService();

    }
    public List<Listing> getListings() {
        return listingService.getAllListings();
    }
    public Listing createListing(Listing newListing) {
        return listingService.createListing(newListing);
    }
    public Listing updateListing(int id, Listing updatedListing) {
            return listingService.updateListing(id, updatedListing);
    }
    public boolean deleteListing(int id) {
        return listingService.deleteListing(id);
    }

    
} 