package com.example.listings;
import java.util.ArrayList;
import java.util.List;

public class ListingService {
    public List<String> getAllListings() {
        List<String> listing = new ArrayList<>();
        listings.add("Listing 1");
        listings.add("Listing 2");
        listings.add("listing 3");
        return listings;
        public Listing createListing(Listing newListing) {
            //Creates a new listing
        }
        public Listing updateListing(int id, Listing updatedListing) {
            if (listingRepository.existsById(id)) {
                updatedListing.setId(id);
                return listingRepository.save(updatedListing);
            }
            
            //updates an existing listing
            return null; // listing is not found
        }
        public boolean deleteLIsting(int id) {
            //logic to delete a listing by ID
        }


    }
}