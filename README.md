# BusReservationSystem
this Project is combined with two parts client Android Application and server in RestFul webservises. the client must be register inorder to harness the features, Registertion and login is requested from the Android app communicating to server using RestFul webservices.once logged in the passanger must enter the source and destination he wants, the app displays all the possible bus and their paths also if more than one path exist between source and destination an option of via path is also provided to choose the best suitable path upon choosing the path user can track the bus live current location.on boarding unto the bus the passenger can buy ticket from his android app and money is paid using his wallet inbuilt in the app and can also add money into it (wallet is just virtual wallet in which money gets added and deducted on buying the tickets).On the conductor side he generates tickets for each journey from which when user(customer)buy get reduced from over all generated tickets. the tracking module is nothing but tracking the conductors current location which works without any special hardware for tracking the bus and takes advantage of the mobile location sensor to send his current coordinates (lat-long)to server.the server sends the location of last record inside the database that ensures the location sent is correct and updated.
Tools and technology used- for client side app we used andoid while for server side we used the java restful webservices using Jax-rx along with mysql as its seql server,webapp deployed on apache tomcat. 