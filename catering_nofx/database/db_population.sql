-- Inserting data for table `Events`
INSERT INTO `Events` (`id`, `name`, `date_start`, `date_end`, `expected_participants`, `organizer_id`)
VALUES (1, 'Wedding Reception', '2023-07-15', '2023-07-15', 200, 2),
       (2, 'Corporate Gala', '2023-08-01', '2023-08-01', 300, 3),
       (3, 'Birthday Party', '2023-09-20', '2023-09-20', 100, 1);

-- Inserting data for table `Menus`
INSERT INTO `Menus` (`id`, `title`, `owner_id`, `published`)
VALUES (1, 'Spring Wedding Menu', 1, 1),
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
       (2, 1, 1, 'Chicken Parmesan', 5, 2),
       (3, 2, 2, 'Seared Salmon', 8, 1),
       (4, 2, 2, 'Roasted Potatoes', 18, 2),
       (5, 3, 3, 'Chocolate Mousse', 19, 1);

-- Inserting data for table `MenuFeatures`
INSERT INTO `MenuFeatures` (`menu_id`, `name`, `value`)
VALUES (1, 'Vegetarian', 1),
       (2, 'Gluten Free', 1),
       (3, 'Vegan', 0);

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
INSERT INTO `CookingProcedures` (`name`, `type`, `fk_referenced_recipe`, `fk_referenced_preparation`)
VALUES ('Boil Pasta', 'recipe', 1, NULL),
       ('Prepare Sauce', 'recipe', 2, NULL),
       ('Bake Bread', 'recipe', 3, NULL),
       ('Mix Salad', 'preparation', NULL, 4),
       ('Grill Chicken', 'recipe', 5, NULL),
       ('Fry Potatoes', 'recipe', 6, NULL),
       ('Steam Vegetables', 'recipe', 7, NULL),
       ('Chop Onions', 'preparation', NULL, 8),
       ('Prepare Dough', 'preparation', NULL, 9),
       ('Marinate Beef', 'preparation', NULL, 10);

-- Inserting data for table `Roles`
INSERT INTO `Roles` (`id`, `role`)
VALUES ('c', 'cuoco'),
       ('h', 'chef'),
       ('o', 'organizzatore'),
       ('s', 'servizio');

-- Inserting data for table `Services`
INSERT INTO `Services` (`id`, `event_id`, `name`, `proposed_menu_id`, `approved_menu_id`, `service_date`, `time_start`,
                        `time_end`, `expected_participants`)
VALUES (1, 1, 'Lunch Service', 1, 1, '2023-07-15', '12:00:00', '15:00:00', 200),
       (2, 2, 'Dinner Service', 2, 2, '2023-08-01', '18:00:00', '21:00:00', 300),
       (3, 3, 'Cake and Coffee', 3, 3, '2023-09-20', '17:00:00', '20:00:00', 100);

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
       (5, 'c'), -- Eva as Chef
       (6, 'h'), -- Frank as Head Chef
       (7, 'o'), -- Georgia as Organizer
       (8, 's'), -- Henry as Service Staff
       (9, 'c'), -- Isla as Chef
       (10, 'h');
-- Jack as Head Chef

-- Step 1: Populate Tasks
INSERT INTO `Tasks` (`cooking_procedure_id`, `cooker_id`, `time_to_complete`, `completed`, `to_prepare`)
VALUES
    (1, 5, 'PT2H', FALSE, TRUE),
    (2, 4, 'PT1H', FALSE, TRUE),
    (3, 9, 'PT3H', FALSE, TRUE);

-- Step 2: Populate SummarySheets
INSERT INTO `SummarySheets` (`id`)
VALUES (NULL);

-- Step 3: Populate ListedTasks
INSERT INTO `ListedTasks` (`summary_sheet_id`, `task_id`)
VALUES
    (1, 1),
    (1, 2),
    (1, 3);

