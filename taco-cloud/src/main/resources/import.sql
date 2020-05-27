if (select count(1) from Taco_Order_Tacos) > 0
begin
    Truncate Table Taco_Order_Tacos;
End
if (select count(1) from Taco_Ingredients) > 0
begin
    Truncate Table Taco_Ingredients;
End
if (select count(1) from Taco) > 0
begin
    Truncate Table Taco;
End
if (select count(1) from Taco_Order) > 0
begin
    Truncate Table Taco_Order;
End
if (select count(1) from Taco_Order_Tacos) > 0
begin
    Truncate Table Taco_Order_Tacos;
End
if (select count(1) from Ingredient) > 0
begin
    Truncate Table Ingredient;
End
insert into Ingredient (id, name, type) values ('FLTO', 'Flour Tortilla', 'WRAP');
insert into Ingredient (id, name, type) values ('COTO', 'Corn Tortilla', 'WRAP');
insert into Ingredient (id, name, type) values ('GRBF', 'Ground Beef', 'PROTEIN');
insert into Ingredient (id, name, type) values ('CARN', 'Carnitas', 'PROTEIN');
insert into Ingredient (id, name, type) values ('TMTO', 'Diced Tomatoes', 'VEGGIES');
insert into Ingredient (id, name, type) values ('LETC', 'Lettuce', 'VEGGIES');
insert into Ingredient (id, name, type) values ('CHED', 'Cheddar', 'CHEESE');
insert into Ingredient (id, name, type) values ('JACK', 'Monterrey Jack', 'CHEESE');
insert into Ingredient (id, name, type) values ('SLSA', 'Salsa', 'SAUCE');
insert into Ingredient (id, name, type) values ('SRCR', 'Sour Cream', 'SAUCE');
insert into User (id, username, password, fullname, street, city, state, zip, phone_number) values ('1000', 'downeyt', '{bcrypt}$2a$10$Va7QUiWYI5NoDN75B209f.nVCzjd6GCNZGG3BWNL07IrGZIVmlpmO', 'Tim Downey', '6551 SW 76 ST', 'South Miami', 'FL', '33143', '3056619555');