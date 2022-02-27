package com.assignment.addressbook.service;

import com.assignment.addressbook.exception.ValidationException;
import com.assignment.addressbook.model.Contact;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private static final String ADDRESS_BOOK_MELBOURNE = "address-book-melbourne";
    private static final String ADDRESS_BOOK_SYDNEY = "address-book-sydney";
    private static final String ADDRESS_BOOK_BRISBANE = "address-book-brisbane";
   
    private final Map<String, List<Contact>> addressBooks = new HashMap<>();

    public ContactService() {
        addressBooks.put(ADDRESS_BOOK_MELBOURNE, new ArrayList<>());
        addressBooks.put(ADDRESS_BOOK_SYDNEY, new ArrayList<>());
        addressBooks.put(ADDRESS_BOOK_BRISBANE, new ArrayList<>());

        this.addContact(ADDRESS_BOOK_MELBOURNE, new Contact("Pavithra", "Manivannan", Arrays.asList("0421435578")));
        this.addContact(ADDRESS_BOOK_MELBOURNE, new Contact("Lisa", "Anderson", Arrays.asList("0421445590")));

        this.addContact(ADDRESS_BOOK_SYDNEY, new Contact("Steven", "Bailey", Arrays.asList("0435000123")));
        this.addContact(ADDRESS_BOOK_SYDNEY, new Contact("Victor", "Anderson", Arrays.asList("0421445963")));

        this.addContact(ADDRESS_BOOK_BRISBANE, new Contact("Arav", "Anderson", Arrays.asList("0435000125")));
        this.addContact(ADDRESS_BOOK_BRISBANE, new Contact("Kash", "Singh", Arrays.asList("0421445909")));
    }

    public Contact addContact(String addressBookId, Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        if (!addressBooks.containsKey(addressBookId)) {
            addressBooks.put(addressBookId, new ArrayList<>());
        }
        addressBooks.get(addressBookId).add(contact);
        return contact;
    }

    public Contact removeContact(String addressBookId, final String contactId) {
        if (!addressBooks.containsKey(addressBookId)) {
            throw new ValidationException("AddressBook with the given Id does not exist");
        }
        Optional<Contact> contactToBeRemoved = addressBooks.get(addressBookId).stream()
                .filter(contact -> contact.getId().equals(contactId)).findFirst();
        if (contactToBeRemoved.isPresent()) {
            addressBooks.get(addressBookId).remove(contactToBeRemoved.get());
            return contactToBeRemoved.get();
        } else {
            throw new ValidationException("The given contact does not exist!");
        }
    }

    public List<Contact> retrieveContacts(String addressBookId) {
        if (!addressBooks.containsKey(addressBookId)) {
            throw new ValidationException("AddressBook with the given Id does not exist");
        }
        return addressBooks.get(addressBookId);
    }

    public List<Contact> retrieveAllUniqueContacts(boolean unique) {
        // assumed that the contact is unique it's name and numbers matches
        List<Contact> contacts = new ArrayList<>();
        addressBooks.values().forEach(contacts::addAll);
        if (unique) {
            return contacts.stream().distinct().collect(Collectors.toList());
        }
        return contacts;
    }
}
