package CampusFlea.demo.controller;

import CampusFlea.demo.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemListRepository extends JpaRepository<Listing,Integer> {
}
