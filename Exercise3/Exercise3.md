# Background:

The business wants a new process that can be used at the gate related to cancelled flights. When a flight is cancelled due to maintenance or crew issues they want a process what will enable them to send a hotel voucher notification via the American Airlines app or SMS text to passengers that have checked in for the flight if there are not more available flights to their destination that day. The business believes that this will reduce the lines at the passenger service counter when the passenger cannot be rebooked on that same day and they must stay the night.

Other groups we’ll need to work with and their available services below, we might not need to use all these services:

# Operations Hub:

1. Flight Status Event Hub – This is an event hub that the Operations Hub group publishes flight events to such as delays and cancellations.

2. Flight Status service – this service will take a request that has flight number, origin, and date. This service would return the status of the flight and any details such as a delay or cancellation and the cancellation reason.

# Notification Hub:

Hotel/Meal/Transportation Notification service – this service will take a request that has a reservation code and notification type (such as hotel, meal, or transportation) and they handle the push to the AA app or SMS message based on the customer preferences

# Sabre Web services:

Passengers checked-in service - this service will return a list of all passengers checked in on a flight. The request is the flight number, origin, and date and the response is a list of each passenger that has checked and all the properties of that passenger, one of these is their reservation code.



Passengers booked service – this service will return a list of all passengers booked on a flight. The request is the flight number, origin, and date and the response is a list of each passenger that has a booking on the flight and all the properties of that passenger, one of these is their reservation code.

Flight availability service – this service is used to search for flights and their availability information for a given origin, destination, and travel date.



# Key Points:

Only send out the notification to passengers that were checked in on flights that were cancelled for maintenance or crew, not weather

If there are more nonstop flights to the destination do not trigger this process. Most of the time this process will happen later at night if the last flight is cancelled.

Agents at the gate will have access to the front-line applications



# What’s needed from you?

Design diagram of how you would architect this solution and a quick explanation of it. We are looking for an architecture diagram and sequence diagram (something that would show the interactions) 