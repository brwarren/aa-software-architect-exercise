# Exercise 2

## infrastructure and hosting

# Problem

Your system is hosted in Azure and you have a need to consume messages that are on an Event Hub topic, transform them to your domain model, and store the updated model.  You must also provide a service to retrieve the domain model.

# Specifications

## Service

- Calls to the service will come from many other systems at the airline
- The service and message processing must be highly available

## Incoming messages

- All messages have a GUID and a timestamp in ISO datetime format in UTC
- ### FLIGHT
    - Designates a flight on the schedule and signifies the first point in time where the system needs to start tracking the flight

| Field                    | Type                             |
|--------------------------|----------------------------------|
| Flight Number            | Integer between 1 and 9999       |
| Origin                   | 3 char alphanumeric airport code |
| Destination              | 3 char alphanumeric airport code |
| Departure date           | ISO date                         |
| Scheduled departure time | ISO datetime with offset         |
| Scheduled arrival time   | ISO datetime with offset         |
| Departure gate           | 5 char alphanumeric              |
| Arrival gate             | 5 char alphanumeric              |

- ### ETD
    - Update to the estimated departure time

| Field                    | Type                             |
|--------------------------|----------------------------------|
|Flight Number|Integer between 1 and 9999|
|Origin|3 char alphanumeric airport code|
|Destination|3 char alphanumeric airport code|
|Departure date|ISO date|
|Estimated departure time|ISO datetime with offset|

- ### DEPARTURE\_GATE
    - Update to departure gate

| Field                    | Type                             |
|--------------------------|----------------------------------|
|Flight Number|Integer between 1 and 9999|
|Origin|3 char alphanumeric airport code|
|Destination|3 char alphanumeric airport code|
|Departure date|ISO date|
|Departure gate|5 char alphanumeric|

- ### ETA
    - Update to estimated arrival time

| Field                    | Type                             |
|--------------------------|----------------------------------|
|Flight Number|Integer between 1 and 9999|
|Origin|3 char alphanumeric airport code|
|Destination|3 char alphanumeric airport code|
|Departure date|ISO date|
|Estimated arrival time|ISO datetime with offset|

- ### ARRIVAL\_GATE
    - Update to arrival gate

| Field                    | Type                             |
|--------------------------|----------------------------------|
|Flight Number|Integer between 1 and 9999|
|Origin|3 char alphanumeric airport code|
|Destination|3 char alphanumeric airport code|
|Departure date|ISO date|
|Arrival gate|5 char alphanumeric|

- ### DEPART
    - Indicates that the flight has departed

| Field                    | Type                             |
|--------------------------|----------------------------------|
|Flight Number|Integer between 1 and 9999|
|Origin|3 char alphanumeric airport code|
|Destination|3 char alphanumeric airport code|
|Departure date|ISO date|
|Actual departure time|ISO datetime with offset|

- ### ARRIVE
    - Indicates the flight has arrived

| Field                    | Type                             |
|--------------------------|----------------------------------|
|Flight Number|Integer between 1 and 9999|
|Origin|3 char alphanumeric airport code|
|Destination|3 char alphanumeric airport code|
|Departure date|ISO date|
|Actual arrival time|ISO datetime with offset|

## Domain object for flight

| Field                    | Type                             |
|--------------------------|----------------------------------|
|Flight Number|Integer between 1 and 9999|
|Origin|3 char alphanumeric airport code|
|Destination|3 char alphanumeric airport code|
|Departure date|ISO date|
|Scheduled departure time|ISO datetime with offset|
|Estimated departure time|ISO datetime with offset|
|Actual departure time|ISO datetime with offset|
|Scheduled arrival time|ISO datetime with offset|
|Estimated arrival time|ISO datetime with offset|
|Actual arrival time| ISO datetime with offset|
|Departure gate|5 char alphanumeric|
|Arrival gate|5 char alphanumeric|
|Aircraft registration number|6 char alphanumeric|

# Flow

1. Flight message is received
    1. Call a web service hosted on prem that provides the aircraft registration number for the flight
1. Other messages are received to update the flight
    1. Culminating in the ARRIVE message

# Solution

The development team has brought forward some ideas for architecting the solution

1. ### Process the messages using an Azure Stream Analytics job
    1. Store the data in a database and join to another table that is a cache of the aircraft registration numbers by flight
    1. Create the flight lookup web service as an Azure Function app that queries the database
1. ### Create a Springboot app for message processing and hosting the flight lookup service
    1. Use Spring Kafka to read the messages
    1. Message consumer
        1. Calls the service to lookup the aircraft registration
        1. Queries the flight from the data store
        1. Updates the flight based on the message
        1. Stores the flight
    1. Flight lookup service
        1. Controller in the same application
    1. Deployment
        1. Container in Azure Kubernetes Services or directly on Azure App Services
1. ### A combination of 1 and 2
    1. Message processing with Azure Stream Analytics
    1. Springboot flight lookup service

# Conclusion

Analyze the options provided by the team and include any other options you would like to consider.  Be sure to list the pros and cons of each option you are considering.  Provide a recommended solution that takes into account the HA requirement and includes:

- Listing of the pros and cons for each solution you are considering
- An architecture diagram of your recommendation
    - Indicate number of instances and/or scaling parameters for each deployed artifact
