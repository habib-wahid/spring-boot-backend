INSERT INTO usba.adm_user_type
VALUES (1, 'BACK_OFFICE', 'Back office user'),
       (2, 'CORPORATE', 'Corporate user'),
       (3, 'AGENT', 'Agent user')
ON CONFLICT DO NOTHING;

INSERT INTO inv_airport(id, name, version)
VALUES (1, 'Dhaka', 0),
       (2, 'Kolkata', 0),
       (3, 'Qatar', 0);

insert into usba.adm_currency(id, name, code, version)
values (1, 'US Dollar', 'USD', 0),
       (2, 'Euro', 'euro', 0),
       (3, 'Taka', 'BDT', 0),
       (4, 'India Rupee', 'INR', 0)
on conflict do nothing;

-- Create Point of Sale Table
insert into usba.adm_point_of_sale(id, name, version)
values (1, 'Dhaka', 0),
       (2, 'Chittagong', 0),
       (3, 'Delhi', 0),
       (4, 'Doha', 0),
       (5, 'NewYork', 0)
on conflict do nothing;

-- initialize airport tablle

insert into usba.inv_airport(id, name, version)
values (1, 'Dhaka', 0),
       (2, 'Chittagong', 0),
       (3, 'Qatar', 0),
       (4, 'Kolkata', 0)
on conflict do nothing;

INSERT INTO usba.adm_user_airport_mapping(user_id, airport_id)
VALUES (1, 2),
       (1, 3);


INSERT INTO usba.adm_user_currency_mapping(user_id, currency_id)
VALUES (1, 1),
       (1, 3);

UPDATE usba.adm_user
set user_type_id =1
WHERE id = 1;

UPDATE usba.adm_user
SET point_of_sale_id = 1
WHERE id = 1;

UPDATE usba.adm_user
SET access_level = 'APPROVER'
WHERE id = 1;

UPDATE usba.adm_user
SET user_code = 'adm'
WHERE id = 1;