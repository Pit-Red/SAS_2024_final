-- Inserting data for table `Roles`
INSERT INTO `Roles` (`id`, `role`)
VALUES ('c', 'cuoco'),
       ('h', 'chef'),
       ('o', 'organizzatore'),
       ('s', 'servizio');


-- Inserting data for table `Users`
INSERT INTO `Users` (`id`, `username`)
VALUES (1, 'Alice'),
       (2, 'Bob'),
       (3, 'Charlie'),
       (4, 'Dana'),
       (5, 'Eva'),
       (6, 'Frank'),
       (7, 'Georgia'),
       (8, 'Henry'),
       (9, 'Isla'),
       (10, 'Jack');

-- Inserting data for table `UserRoles`
INSERT INTO `UserRoles` (`user_id`, `role_id`)
VALUES (1, 'o'),
       (2, 'h'),
       (3, 's'),
       (4, 'c'),
       (5, 'h'), -- Eva as Chef
       (6, 'h'), -- Frank as Head Chef
       (7, 'o'), -- Georgia as Organizer
       (8, 's'), -- Henry as Service Staff
       (9, 'c'), -- Isla as Chef
       (10, 'h'); -- Jack as Head Chef

-- Inserting data for table `Events`
INSERT INTO `Events` (`id`, `name`, `date_start`, `date_end`, `expected_participants`, `organizer_id`)
VALUES (1, 'Wedding Reception', '2023-07-15', '2023-07-15', 200, 2),
       (2, 'Corporate Gala', '2023-08-01', '2023-08-01', 300, 3),
       (3, 'Birthday Party', '2023-09-20', '2023-09-20', 100, 1);



-- Inserting data for table `Preparations`
INSERT INTO `Preparations` (`id`)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9),
       (10);

-- Inserting data for table `Recipes`
INSERT INTO `Recipes` (`id`)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9),
       (10);

-- Inserting data for table `RecipePreparations`
INSERT INTO `RecipePreparations` (`recipe_id`, `preparation_id`)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- Inserting data for table `CookingProcedures`
INSERT INTO `CookingProcedures` (`id`, `name`, `type`, `fk_referenced_recipe`, `fk_referenced_preparation`)
VALUES (1, 'Boil Pasta', 'recipe', 1, NULL),
       (2, 'Prepare Sauce', 'recipe', 2, NULL),
       (3, 'Bake Bread', 'recipe', 3, NULL),
       (4, 'Mix Salad', 'preparation', NULL, 4),
       (5, 'Grill Chicken', 'recipe', 5, NULL),
       (6, 'Fry Potatoes', 'recipe', 6, NULL),
       (7, 'Steam Vegetables', 'recipe', 7, NULL),
       (8, 'Chop Onions', 'preparation', NULL, 8),
       (9, 'Prepare Dough', 'preparation', NULL, 9),
       (10, 'Marinate Beef', 'preparation', NULL, 10);

-- Inserting data for table `Menus`
INSERT INTO `Menus` (`id`, `title`, `owner_id`, `published`)
VALUES (1, 'Spring Wedding Menu', 5, 1),
       (2, 'Corporate Dinner', 3, 1),
       (3, 'Birthday Desserts', 2, 1);

-- Inserting data for table `MenuSections`
INSERT INTO `MenuSections` (`id`, `menu_id`, `name`, `position`)
VALUES (1, 1, 'Appetizers', 1),
       (2, 1, 'Main Course', 2),
       (3, 2, 'Desserts', 3);

-- Inserting data for table `MenuItems`
INSERT INTO `MenuItems` (`id`, `menu_id`, `section_id`, `description`, `recipe_id`, `position`)
VALUES (1, 1, 1, 'Tomato Soup', 1, 1),
       (2, 1, 2, 'Chicken Parmesan', 5, 2),
       (3, 2, 3, 'Seared Salmon', 8, 1),
       (4, 2, 3, 'Roasted Potatoes', 18, 2),
       (5, 3, 3, 'Chocolate Mousse', 19, 1);

-- Inserting data for table `MenuFeatures`
INSERT INTO `MenuFeatures` (`menu_id`, `name`, `value`)
VALUES (1, 'Vegetarian', 1),
       (2, 'Gluten Free', 1),
       (3, 'Vegan', 0);

-- Inserting data for table `Tasks`
INSERT INTO `Tasks` (`id`, `cooking_procedure_id`, `cook_id`, `time_to_complete`, `completed`, `to_prepare`)
VALUES (1, 5, 9, 'PT2H', FALSE, TRUE),
       (2, 4, 10, 'PT1H30M', FALSE, TRUE),
       (3, 6, 5, 'PT1H', TRUE, TRUE);

-- Inserting data for table `SummarySheets`
INSERT INTO `SummarySheets` (`id`)
VALUES (1), (2), (3);

-- Inserting data for table `ListedTasks`
INSERT INTO `ListedTasks` (`summary_sheet_id`, `task_id`)
VALUES (1, 1),
       (1, 2),
       (1, 3);

-- Inserting data for table `Services`
INSERT INTO `Services` (`id`, `event_id`, `name`, `used_menu_id`, `service_date`, `time_start`, `time_end`, `expected_participants`, `chef_id`, `summary_sheet_id`)
VALUES (1, 1, 'Lunch Service', 1, '2023-07-15', '12:00:00', '15:00:00', 200, 5, 1),
       (2, 2, 'Dinner Service', 2, '2023-08-01', '18:00:00', '21:00:00', 300, 2, 2),
       (3, 3, 'Cake and Coffee', 3, '2023-09-20', '17:00:00', '20:00:00', 100, 6, 3) ;

-- Inserting data for table `ListedProcedures`
INSERT INTO `ListedProcedures` (`summary_sheet_id`, `procedure_id`, `position`)
VALUES
    (1, 1, 0),  -- Boil Pasta at position 0 in Summary Sheet 1
    (1, 2, 1),  -- Prepare Sauce at position 1 in Summary Sheet 1
    (1, 5, 2),  -- Grill Chicken at position 2 in Summary Sheet 1
    (1, 4, 3),  -- Mix Salad at position 3 in Summary Sheet 1

    (2, 3, 0),  -- Bake Bread at position 0 in Summary Sheet 2
    (2, 6, 1),  -- Fry Potatoes at position 1 in Summary Sheet 2
    (2, 7, 2),  -- Steam Vegetables at position 2 in Summary Sheet 2

    (3, 8, 0),  -- Chop Onions at position 0 in Summary Sheet 3
    (3, 9, 1),  -- Prepare Dough at position 1 in Summary Sheet 3
    (3, 10, 2); -- Marinate Beef at position 2 in Summary Sheet 3
