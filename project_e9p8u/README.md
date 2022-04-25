# Expiration Date Tracker

## Language: Java
### CPSC 210 Term Project
**Purpose**

While I was living by myself for the first time, I ended up forgetting the expiration dates for food like cheese
and ham. So, I thought it would be convenient to have some app that could list out all of 
the expiration dates of the foods in my fridge, as well as being able to sort them by expiry dates,
which tell me which food I should prioritize eating and by food type, which I decided to include
as a feature since some food types are not as sensitive to expiration dates and I wanted to distinguish between
these foods.

**Features**:
- takes food item with corresponding food type expiration date (YYYY/MM/DD)
- categorize list of food by *food type* <sup>1</sup> and expiration date
- can remove all expired foods at once



<font size=1> 1.
Examples of food types are to be dairy, meat, non-perishable, seafood, fruits, vegetables and etc.</font>

###User Stories
- As a user, I want to be able to add foods to a fridge
- As a user, I want to be able to remove foods from a fridge
- As a user, I want to be able to sort foods by their food type
- As a user, I want to be able to sort foods by expiration dates
- As a user, I want to be able to remove all foods with passed expiry dates at once
- As a user, I want to be able to save my fridge file
- As a user, I want to be given the option to load my fridge when I start the application

#### Sample of Logged Events
milk has been added to fridge on Thu Nov 25 16:05:46 PST 2021\
cheese has been added to fridge on Thu Nov 25 16:05:46 PST 2021\
tomatoes has been added to fridge on Thu Nov 25 16:05:46 PST 2021\
bananas has been added to fridge on Thu Nov 25 16:05:46 PST 2021\
chicken has been added to fridge on Thu Nov 25 16:05:57 PST 2021\
shrimp has been added to fridge on Thu Nov 25 16:06:15 PST 2021\
Sorted the fridge by food type on Thu Nov 25 16:06:18 PST 2021\
Sorted the fridge by expiry date on Thu Nov 25 16:06:21 PST 2021\
Throwing away all expired foods on Thu Nov 25 16:06:26 PST 2021\
milk was thrown away on Thu Nov 25 16:06:26 PST 2021\
cheese was thrown away on Thu Nov 25 16:06:26 PST 2021

###Phase 4 Task 3:
If I had more time to work on this project, I would make Fridge extend an
abstract class Container because if I wanted to add a freezer compartment or
fruit compartment in my fridge with my current design, I would need to 
add a whole new class with its own implementation of methods like
addFood and removeFood, which would be inefficient. Instead, if I had an abstract Container class, I could
make additional classes like Freezer extend Container and make minimal changes to the
original code.
