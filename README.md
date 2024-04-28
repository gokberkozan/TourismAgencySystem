## Tourism Agency System

The main purpose of this project is to enable the company operating in the hotel sector to manage its daily operations more effectively and to optimize customer reservation processes.

### Summary
This management system was developed for Patika Tourism Agency with the intention of assisting businesses in the tourism sector in managing their daily operations more effectively. 
The system's streamlining of hotel reservation procedures enables agency staff to respond to client inquiries in a timely and effective manner. 
There are two distinct user types that have been identified: administrators and agency personnel. 
These users have the ability to do a wide range of operations on the system based on their authorizations. 
The agency should be able to easily conduct business in the digital realm with the aid of basic features like hotel and room administration, period and price management, room search, and reservation transactions.

### Database
- User: admin and employee (agency employee) user information should be kept.

- Hotel: hotel information registered in the system must be kept.

- Season: season records of the hotel should be kept. There must be a hotel_id column.

- Pension_type: records of the hotel's pension type should be kept. There must be a hotel_id column.

- Room: hotel room records must be kept. There should be hotel_id, season_id, pension_type_id columns.

- Reservation: reservation records for the room must be kept. There should be a room_id column.

### Used technologies
- Java

- Swing

- PostgreSQL database

- IntelliJ IDEA

### Admin
User Management: If you are logged in with administrator privileges, the admin panel will appear. Based on this display,
- Listing agency employees,

- Adding,

- Delete,

- Update and
Filtering should be done according to the user's role (admin, staff).

### Agency Employee (Personnel)
- Hotel Management: Listing and removing accommodations

- Handling Rooms: Adding and Listing Areas

- Managing Your Time: Listing and Incorporating Times

- Cost-Reduction: Listing, adding, removing, and changing reservations for the room search

### User Management
By adding, deleting, and editing users, the administrator controls who has access to the system. Users log into the system by entering their username and password.
- Admin determines the user's role (admin, staff) when adding a new user to the system.

- Admin can edit existing users' information (name, surname, password, etc.).

- Admin can delete user account.

- Admin can filter by user's role (admin, staff)..

### Hotel management
The location data and other system components of the contracted hotels must be managed by the agency. Additional definitions are established for the following while adding a hotel: Hotel details include name, address, phone, email, star, amenities, and pension types.
A screen listing hotels is part of the hotel screen. You can add a hotel on this screen. At the same time, details regarding the type of hostel, amenities, and duration of stay of hotels that are open for business has to be documented.

#### Hostel Types expected to be in the system:
- Ultra All Inclusive

- All inclusive

- Room breakfast

- Full pension

- Half Board

- Bed Only

- Excluding Alcohol Full credit

#### Facility Features expected to be in the system:
- Free Parking

- Free WiFi

- Swimming pool

- Fitness Center

- Hotel Concierge

- SPA

- 24/7 Room Service

### Term Management
Historical hotel periods are taken into account while determining room prices. Providing adjustable prices is the aim here. 
Winter hotel rates are lower than summer ones, despite the latter being higher. 
In the tourism sector, prices are frequently modified. Periods are defined as two sets of dates.
Agency staff members input the periods as ranges of dates. The price of rooms varies at specific periods.

Sample Periods:

01/01/2021 - 31/05/2021

01/06/2021 - 01/12/2021

### Room Management
The agency staff member inputs hotel reservations into the system and establishes rates based on those bookings. 
The hotels will only provide the following four accommodation types: double rooms, single rooms, junior suites, and suites.
Rather than continuously adding rooms of the same type to the system, stock logic is used. 
You must also access the room's features. The aspects of the room are listed in detail below. 
The room screen includes a screen with a list of all the rooms. 
Agency staff can add rooms to hotels and search for available rooms for reservations using this tool.
You can select from the hotel, one of the four types of rooms, a system-registered hostel type, and a system-registered period when you go to the page for adding rooms. 
Based on the selections made, the adult nightly price information, the kid nightly price information, and the stock amount of the room are recorded. 
The number of beds, the square footage of the ensuing room features, and whether or not the other room characteristics are present in the room should also be recorded by the system. 
All of the room's information should be visible on the room listing screen. Expected system room features:

- Number of Beds

- Square meters
  
- Television (Yes, No)

- Minibar (Yes, No)
  
- Game Console (Yes, No)

- Safe (Yes, No)

- Projection (Yes, No)

The capacity of a room depends on the number of beds. 2 guests can be added to a 2-bed room.

### Room Pricing
The cost of a room is set for each night. 
Depending on the type of hostel and when the hotel opens, different rates will apply. 
Adult and child costs will be different.

When setting prices, the cost of the rooms is taken into account each night.

### Room Search and Reservation Procedures
Employee of the agency using the system

- The date range it enters,

- To the city,

- On behalf of the hotel

Must be able to search for rooms accordingly.

A search should be possible if one, two, or all three of the previously listed search conditions are submitted. To accomplish this, you must write the necessary dynamic SQL query.

### Room Search Algorithm
The agency can list the rooms defined in the system after inputting the required room search data.

In order for rooms to be listed:

- The hotel in which the accommodation is situated must be in the intended city.

- The hotel must be aware of the duration in line with the chosen window of time. For example, if a search is conducted with the check-in date of 09/06/2021 and the check-out date of 12/06/2021, hotels are required to provide a period within that date range.

- If there is a hotel period, the customer must be informed of the room's price in line with the hostel types during the relevant periods.

- The stock number of the room must be more than zero.

### Price Calculation
The number of nights to stay, the room price, and guest information are used to determine the prices.

Considering this data
- The city that was initially entered had hotels.

- Rooms at the hotel can be rented.

- If periodic pricing data for the rooms is available within the relevant time window, a price computation is performed.

### Reservation Process
After listing the rooms with the attributes the customer wants, the agency user proceeds to book the customer's preferred room. The full cost should be calculated automatically, one of the clients' contact details should be gathered, and the reservation should be completed as soon as the procedure starts.

To complete the reservation:

- Contact details for the customer

- Information on the guest's name, surname, and T.R. ID

uses the system to complete the transaction. After the transaction is completed, the relevant room's inventory will decrease by one.

Staff members of the agency will list, amend, and remove reservations placed on the system. For canceled reservations, the available supply of the relevant room must be raised by one.
