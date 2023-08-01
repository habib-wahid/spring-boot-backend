-- Create Admin Group
insert into adm_group(id, name, description, version)
values (1, 'ADMIN', 'Admin', 0)
on conflict do nothing;

-- Create Department table
insert into adm_department(id, name, version)
values (1, 'admin', 0)
on conflict do nothing;

-- Create Designation Table
insert into adm_designation(id, name, version)
values (1, 'manager', 0)
on conflict do nothing;

-- Create Currency Table
insert into adm_currency(id, name, code, version)
values (1, 'US Dollar', 'USD', 0),
       (2, 'Euro', 'euro', 0),
       (3, 'Taka', 'BDT', 0),
       (4, 'India Rupee', 'INR', 0)
on conflict do nothing;

-- Create Point of Sale Table
insert into adm_point_of_sale(id, name, version)
values (1, 'Dhaka', 0),
       (2, 'Chittagong', 0),
       (3, 'Delhi', 0),
       (4, 'Doha', 0),
       (5, 'NewYork', 0)
on conflict do nothing;

-- Create Password Policy
insert into adm_password_policy(id, password_length, contains_digit, contains_lowercase, contains_special_characters,
                                     contains_uppercase, version)
values (1, 1, false, false, false, false, 0)
on conflict do nothing;

-- Create Personal Info Table
insert into adm_personal_info(id, first_name, last_name, email_official, department_id, designation_id, point_of_sale_id, version)
values (1, 'super', 'admin', 'admin@gmail.com', 1, 1, 1, 0)
on conflict do nothing;

-- Create Super Admin => username='admin' password='admin'
insert into adm_user(id, email, username, password, group_id, active, version, personal_info_id)
values (1, 'admin@gmail.com', 'admin',
        '$2a$10$FszQtoGSFc/WDJZvOUQiQeGhGiw7KwwXh7tMRbMBpUtYLE.PGmM/y', 1, true, 0, 1)
on conflict do nothing;


-- Set data on personal_info currency mapping table
insert into adm_personal_info_currency_mapping(personal_info_id, currency_id)
values (1, 1),
       (1, 2)
on conflict do nothing;


-- Insert Modules
insert into adm_module (id, name, description, sort_order, version)
    (values (1, 'INVENTORY', 'Inventory', 1, 0),
            (2, 'PRICING', 'Pricing', 2, 0),
            (3, 'SALES', 'Sales', 3, 0),
            (4, 'DCS', 'DCS', 4, 0),
            (5, 'REPORT', 'Reports', 5, 0),
            (6, 'ADMIN', 'Administration', 6, 0),
            (7, 'CENTRAL_CONTROL', 'Central Control', 7, 0),
            (8, 'MESSAGE', 'Message', 8, 0),
            (9, 'BOOK_A_FLIGHT', 'Book a Flight', 9, 0),
            (10, 'ACCOUNTING', 'Accounting', 10, 0))
on conflict do nothing;

insert into adm_sub_module (id, name, description, module_id, sort_order, version)
    (values (1, 'INVENTORY_BASE_PARAMETER', 'Base Parameters', 1, 1, 0),
            (2, 'INVENTORY_FLIGHT_MANAGEMENT', 'Flights Management', 1, 2, 0),
            (3, 'INVENTORY_GDS', 'GDS/Partners Parameters', 1, 3, 0),
            (4, 'PRICING_CLASS_MANAGEMENT', 'Classes Management', 2, 4, 0),
            (5, 'PRICING_FARE_MANAGEMENT', 'Fares Management', 2, 5, 0),
            (6, 'PRICING_TAX_AND_PENALTY', 'Taxes & Penalties', 2, 6, 0),
            (7, 'PRICING_OTHER_SETTING', 'Other Settings', 2, 7, 0),
            (8, 'SALES_BOOKING', 'Booking', 3, 8, 0),
            (9, 'SALES_SEARCH', 'Search', 3, 9, 0),
            (10, 'SALES_CUSTOMER', 'Customers', 3, 10, 0),
            (11, 'SALES_AGENCY_AND_CORPORATE', 'Agencies & Corporates', 3, 11, 0),
            (12, 'SALES_CASHBOX_MANAGEMENT', 'Cashbox Management', 3, 12, 0),
            (13, 'DCS_DEFAULT', 'DCS', 4, 13, 0),
            (14, 'REPORT_SALES', 'Sales', 5, 14, 0),
            (15, 'REPORT_GDS_AND_INTERLINE_SALES', 'GDS & Interline Sales', 5, 15, 0),
            (16, 'REPORT_TA_AND_CORPORATE_CUSTOMER', 'TA & Corporate Customers', 5, 16, 0),
            (17, 'REPORT_CENTER', 'Report Center', 5, 17, 0),
            (18, 'ADMIN_ACCOUNT_SETTING', 'Account Settings', 6, 18, 0),
            (19, 'ADMIN_SECURITY_AND_ACCESS_RIGHT', 'Security & Access Rights', 6, 19, 0),
            (20, 'ADMIN_QUEUE_MANAGEMENT', 'Queue Management', 6, 20, 0),
            (21, 'ADMIN_ONLINE_HELP', 'Online Help', 6, 21, 0))
on conflict do nothing;


-- Insert Menu
insert into adm_menu(id, name, description, url, icon, screen_id, sub_module_id, sort_order, version)
    (values (1, 'CABIN', 'Cabin', 'cabin', 'cabin', '0001', 1, 1, 0),
            (2, 'AIRCRAFT_TYPE', 'Aircraft Types', 'aircraft types', 'aircraft types', '0002', 1, 2, 0),
            (3, 'AIRCRAFT', 'Aircraft', 'aircraft', 'aircraft', '0003', 1, 3, 0),
            (4, 'AIRPORT', 'Airports', 'airports', 'airports', '0004', 1, 4, 0),
            (5, 'UPDATE_DST', 'Update DST', 'update dst', 'update dst', '0005', 1, 5, 0),
            (6, 'DCS_AIRPORT', 'DCS Airports', 'dcs airports', 'dcs airports', '0006', 1, 6, 0),
            (7, 'CITY_PAIR', 'City Pairs', 'city pairs', 'city pairs', '0007', 1, 7, 0),
            (8, 'PRBD', 'PRBDs', 'prbds', 'prbds', '0008', 1, 8, 0),
            (9, 'OFFER_TEMPLATE', 'Offer Templates', 'offer templates', 'offer templates', '0009', 1, 9, 0),
            (10, 'FLIGHT_SCHEDULE_CREATION', 'Flight Schedule Creation', 'flight schedule creation',
             'flight schedule creation', '0010', 1, 10, 0),
            (11, 'CONNECTING_FLIGHT', 'Connecting Flights', 'connecting flights', 'connecting flights', '0011', 1, 11,
             0),
            (12, 'AUTO_GENERATION_CONNECTING_FLIGHTS', 'Auto Generation of Connecting Flights',
             'auto generation connecting flights', 'auto generation connecting flights', '0012', 1, 12, 0),
            (13, 'GENERATE_TIMETABLE', 'Generate the Timetable', 'generate timetable', 'generate timetable', '0013', 1,
             13, 0),
            (14, 'WAITING_LIST_SETUP', 'Waiting List Setup', 'waiting list setup', 'waiting list setup', '0014', 1, 14,
             0),
            (15, 'SEAT_MAP', 'Seat Map', 'seat map', 'seat map', '0015', 1, 15, 0),
            (16, 'SSR_GROUP', 'SSR Group for Stock Control', 'ssr control', 'ssr control', '0016', 1, 16, 0),
            (17, 'FLIGHT_SCHEDULE', 'Flight Schedules', 'flight schedules', 'flight schedules', '0025', 2, 17, 0),
            (18, 'FLIGHT_LIST', 'Flight List', 'flight list', 'flight list', '0026', 2, 18, 0),
            (19, 'IMPORT_EXPORT_FLIGHT_OFFER', 'Import/Export Flight Offer', 'import export flight offer',
             'import export flight offer', '0027', 2, 19, 0),
            (20, 'FLIGHT_OVERVIEW', 'Flights Overview', 'flights overview', 'flights overview', '0028', 2, 20, 0),
            (21, 'AVAILABILITY_TRACKING', 'Availabilities Tracking', 'availabilities tracking',
             'availabilities tracking', '0029', 2, 21, 0),
            (22, 'CREW_MEMBER', 'Crew Members', 'crew members', 'crew members', '0030', 2, 22, 0),
            (23, 'CREW_SCHEDULING', 'Crew Scheduling', 'crew scheduling', 'crew scheduling', '0031', 2, 23, 0),
            (24, 'CODE_SHARE_SEARCH', 'Code Share Search', 'code share search', 'code eshare search', '0032', 2, 24, 0),
            (25, 'CODE_SHARE_DEFAULT_MAPPING', 'Code Share Default Mapping', 'code share default mapping',
             'code share default mapping', '0033', 2, 25, 0),
            (26, 'GDS_MESSAGE', 'GDS/Partners Message', 'gds message', 'gds message', '0041', 3, 26, 0),
            (27, 'SSIM_EXCHANGE', 'SSIM Exchange', 'ssim exchange', 'ssim exchange', '0042', 3, 27, 0),
            (28, 'IMPORT_SSIM_FILE', 'Import SSIM File', 'import ssim file', 'import ssim file', '0043', 3, 28, 0),
            (29, 'EXPORT_SSIM_FILE', 'Export SSIM File', 'export ssim file', 'export ssim file', '0044', 3, 29, 0),
            (30, 'FARE_BASIS', 'Fare Basis', 'fare basis', 'fare basis', '0051', 4, 30, 0),
            (31, 'TIME_LIMIT_MANAGEMENT', 'Time Limit Management', 'time limit management', 'time limit management',
             '0052', 4,
             31, 0),
            (32, 'WEB_CLASS', 'Web Classes', 'web classes', 'web classes', '0053', 4, 32, 0),
            (33, 'FARE_SEASON', 'Fare Seasons', 'fare season', 'fare season', '0065', 5, 33, 0),
            (34, 'FARE_SETUP', 'Fares Setup', 'fares setup', 'menu_icon7', '0066', 5, 34, 0),
            (35, 'VIEW_FARE', 'View Fares', 'view fares', 'view fares', '0067', 5, 35, 0),
            (36, 'EXPORT_FARE', 'Export Fares', 'export fares', 'export fares', '0068', 5, 36, 0),
            (37, 'FARE_GLOBAL_MODIFICATION', 'Fare Global Modif.', 'fares global mod.', 'fares global mod.', '0069', 5,
             37, 0),
            (38, 'FARE_DUPLICATION', 'Fare Duplication', 'fare duplication', 'fare duplication', '0070', 5, 38, 0),
            (39, 'FARE_CHECK', 'Fare Check', 'fare check', 'fare check', '0071', 5, 39, 0),
            (40, 'PENALTY_SETUP', 'Penalties Setup', 'penalty setup', 'penalty setup', '0081', 6, 40, 0),
            (41, 'PENALTY_WAIVER_SETUP', 'Penalties Waiver Setup', 'penalty waiver setup', 'penalty waiver setup',
             '0082', 6,
             41, 0),
            (42, 'PENALTY_COPY_FROM_FARE_BASIS', 'Penalties Copy from a Fare Basis', 'penalty copy from fare basis',
             'penalty copy from fare basis', '0083', 6, 42, 0),
            (43, 'TAX_SETUP', 'Taxes Setup', 'tax setup', 'tax setup', '0084', 6, 43, 0),
            (44, 'TAX_GLOBAL_MODIFICATION', 'Taxes Global Mod.', 'tax global mod', 'tax global mod', '0085', 6, 44, 0),
            (45, 'EXEMPTION_OF_TAX', 'Exemption of Taxes on Fare Basis', 'exemption of tax', 'exemption of tax', '0086',
             6, 45,
             0),
            (46, 'TAX_SIMULATION', 'Taxes Simulation', 'tax simulation', 'tax simulation', '0087', 6, 46, 0),
            (47, 'EXCHANGE_RATE', 'Exchange Rate', 'exchange rate', 'exchange rate', '0095', 7, 47, 0),
            (48, 'NON_CONVERSION_OF_CURRENCY', 'Non Conversion of Currencies', 'non conversion of currency',
             'non conversion of currency', '0096', 7, 48, 0),
            (49, 'PASSENGER_TYPE', 'Passenger Types', 'passenger type', 'passenger type', '0097', 7, 49, 0),
            (50, 'GENERATE_ATPCO', 'Generate ATPCO', 'generate atpco', 'generate atpco', '0098', 7, 50, 0),
            (51, 'ATPCO_EXPORT', 'ATPCO Export', 'atpco export', 'atpco export', '0099', 7, 51, 0),
            (52, 'OTHER_SERVICE', 'Other Services', 'other service', 'other service', '0100', 7, 52, 0),
            (53, 'ANCILLARY', 'Ancillaries', 'ancillary', 'ancillary', '0101', 7, 53, 0),
            (54, 'TOUR_CODE', 'Tour Code', 'tour code', 'tour code', '0102', 7, 54, 0),
            (55, 'QUICK_SALE', 'Quick Sales', 'quick sale', 'quick sale', '0115', 8, 55, 0),
            (56, 'NEW_PNR', 'New PNR', 'new pnr', 'new pnr', '0116', 8, 56, 0),
            (57, 'GROUP_PNR', 'Group PNR', 'group pnr', 'group pnr', '0117', 8, 57, 0),
            (58, 'PNR_WITHOUT_TICKET', 'PNR without Ticket', 'pnr without ticket', 'pnr without ticket', '0118', 8, 58,
             0),
            (59, 'PNR_ADVANCED_SEARCH', 'PNR Advanced Search', 'pnr advanced search', 'pnr advanced search', '0121', 9,
             59, 0),
            (60, 'TICKET_SEARCH', 'Ticket Search', 'ticket search', 'ticket search', '0122', 9, 60, 0),
            (61, 'COUPON_SEARCH', 'Coupons Search', 'coupon search', 'coupon search', '0123', 9, 61, 0),
            (62, 'MY_PNR', 'My PNRs', 'my pnr', 'my pnr', '0124', 9, 62, 0),
            (63, 'NON_TICKETED_PNR', 'Non-ticketed PNRs', 'non-ticketed pnr', 'non-ticketed pnr', '0125', 9, 63, 0),
            (64, 'WAIT_LIST_PNR', 'Wait List PNRs', 'wait list pnr', 'wait list pnr', '0126', 9, 64, 0),
            (65, 'CONFIRMED_WL', 'Confirmed WL', 'confirmed wl', 'confirmed wl', '0127', 9, 65, 0),
            (66, 'PNR_MODIFICATION_IN_PROGRESS', 'PNR Modification in Progress', 'pnr modification', 'pnr modification',
             '0128',
             9, 66, 0),
            (67, 'EXCESS_BAGGAGE', 'Excess Baggage', 'excess baggage', 'excess baggage', '0129', 9, 67, 0),
            (68, 'PASSENGER_LIST_AND_CONTACT', 'Passenger List & Contact', 'passenger list contact',
             'passenger list contact', '0130', 9, 68, 0),
            (69, 'VIEW_PNL', 'View PNLs', 'view pnl', 'view pnl', '0131', 9, 69, 0),
            (70, 'CHANGE_HISTORY', 'Change History', 'change history', 'change history', '0132', 9, 70, 0),
            (71, 'REVALIDATION_TO_HANDLE', 'Revalidation to Handle', 'revalidation to handle', 'revalidation to handle',
             '0133',
             9, 71, 0),
            (72, 'TIMETABLE', 'Timetable', 'timetable', 'timetable', '0134', 9, 72, 0),
            (73, 'FARE_EXCLUDING_TAX', 'Fares Excluding Taxes', 'fares excluding taxes', 'fares excluding taxes',
             '0135', 9,
             73, 0),
            (74, 'FLIGHT_IRREGULARITY_REPORT', 'Flight Irregularity Report', 'flight irregularity report',
             'flight irregularity report', '0136', 9, 74, 0),
            (75, 'CUSTOMER_ADVANCED_SEARCH', 'Customer Advanced Search', 'customer advanced search',
             'customer advanced search', '0141', 10, 75, 0),
            (76, 'CUSTOMER_CREATION', 'Customer Creation', 'customer creation', 'customer creation', '0142', 10, 76, 0),
            (77, 'EMD_MANAGEMENT', 'EMD Management', 'emd management', 'emd management', '0143', 10, 77, 0),
            (78, 'FFP_LEVEL_CONFIGURATION', 'FFP Level Configuration', 'ffp configuration', 'ffp configuration', '0144',
             10, 78,
             0),
            (79, 'FFP_CUSTOMER_SEARCH', 'FFP Customer Search', 'ffp customer search', 'ffp customer search', '0145', 10,
             79, 0),
            (80, 'CRM_TRACKING', 'CRM Tracking', 'crm tracking', 'crm tracking', '0146', 10, 80, 0),
            (81, 'CORPORATE_CREATION', 'Corporate Creation', 'corporate creation', 'corporate creation', '0151', 11, 81,
             0),
            (82, 'EXTRANET_AGENCY_CREATION', 'Extranet Agency Creation', 'agency creation', 'agency creation', '0152',
             11, 82,
             0),
            (83, 'COMMISSION_GLOBAL_MODIFICATION', 'Commission Global Modification', 'commission modification',
             'commission modification', '0153', 11, 83, 0),
            (84, 'AGENCY_PENDING_AUTHORIZATION', 'Agencies Pending Authorizations', 'agency authorization',
             'agency authorization', '0154', 11, 84, 0),
            (85, 'CASHBOX_CLOSURE', 'Cashbox Closure', 'cashbox closure', 'cashbox closure', '0161', 12, 85, 0),
            (86, 'CASHBOX_CLOSING_HISTORY', 'Cashbox Closing History', 'cashbox history', 'cashbox history', '0162', 12,
             86, 0),
            (87, 'REFUNDED_PNR', 'Refunded PNRs', 'refunded pnr', 'refunded pnr', '0163', 12, 87, 0),
            (88, 'FLIGHT_MANAGEMENT', 'Flights Management', 'flight management', 'flight management', '0171', 13, 88,
             0),
            (89, 'DCS_MESSAGE', 'DCS Messages', 'dcs message', 'dcs message', '0172', 13, 89, 0),
            (90, 'DCS_MESSAGE_TO_HANDLE', 'DCS Messages to Handle', 'dcs message to handle', 'dcs message to handle',
             '0173',
             13, 90, 0),
            (91, 'COUPON_SUMMARY', 'Coupons Summary', 'coupon summary', 'coupon summary', '0175', 14, 91, 0),
            (92, 'CASH_STATEMENT', 'Cash Statements', 'cash statement', 'cash statement', '0176', 14, 92, 0),
            (93, 'SALES_STATEMENT', 'Sales Statements', 'sales statement', 'sales statement', '0177', 14, 93, 0),
            (94, 'GDS_COUPON_SUMMARY', 'Coupons Summary', 'gds coupon summary', 'gds coupon summary', '0185', 15, 94,
             0),
            (95, 'GDS_SALES_STATEMENT', 'Sales Statements', 'gds sales statement', 'gds sales statement', '0186', 15,
             95, 0),
            (96, 'ON_ACCOUNT_SALES_SYNTHESIS', 'On Account Sales Synthesis', 'sales synthesis', 'sales synthesis',
             '0195', 16,
             96, 0),
            (97, 'SALES_REPORT_MANAGEMENT', 'Sales Report Management', 'sales report management',
             'sales report management', '0196', 16, 97, 0),
            (98, 'TA_SALES_SYNTHESIS', 'TA Sales Synthesis', 'ta sales synthesis', 'ta sales synthesis', '0197', 16, 98,
             0),
            (99, 'DYNAMIC_REPORT', 'Dynamic Reports', 'dynamic report', 'dynamic report', '0201', 17, 99, 0),
            (100, 'STATIC_REPORT', 'Static Reports', 'static report', 'static report', '0202', 17, 100, 0),
            (101, 'MY_ACCOUNT', 'My Account', 'my account', 'my account', '0211', 18, 101, 0),
            (102, 'USER_GROUP', 'User Groups', 'group', 'group', '0221', 19, 102, 0),
            (103, 'USER', 'Users', 'user', 'user', '0222', 19, 103, 0),
            (104, 'REPORT_CENTER_USER_RIGHT', 'Report Center User Rights', 'report center user right',
             'report center user right', '0223', 19, 104, 0),
            (105, 'ZENITH_ACCESS', 'Zenith Access', 'zenith access', 'zenith access', '0224', 19, 104, 0),
            (106, 'REFUND_REQUEST_MANAGEMENT', 'Refund Request Management', 'refund request management',
             'refund request management', '0225', 19, 106, 0),
            (107, 'BLACKLIST_AND_WHITELIST_PERSON', 'Blacklist & Whitelist Person', 'blacklist whitelist',
             'blacklist whitelist', '0226', 19, 107, 0),
            (108, 'TICKETS_TO_PROTECT', 'Tickets to Protect', 'tickets to protect', 'tickets to protect', '0231', 20,
             108, 0),
            (109, 'DOCUMENTATION', 'Documentation', 'documentation', 'documentation', '0241', 21, 109, 0),
            (110, 'USER_STATUS', 'User Status', 'user status', 'user status', '0242', 21, 110, 0),
            (111, 'PROCESSING_QUEUE', 'Processing Queue', 'processing queue', 'processing queue', '0243', 21, 111, 0),
            (112, 'PNR_GOV', 'PNR Gov', 'pnr gov', 'pnr gov', '0244', 21, 112, 0))
on conflict do nothing;

-- Insert Action
insert into adm_action(id, name, description, menu_id, sort_order, version)
    (values (1, 'VIEW_CABIN', 'View Cabins', 1, 1, 0),
            (2, 'CREATE_CABIN', 'Create a Cabin', 1, 2, 0),
            (3, 'ACTIVATE_CABIN', 'Activate a Cabin', 1, 3, 0),
            (4, 'DELETE_CABIN', 'Delete a Cabin', 1, 4, 0),
            (5, 'VIEW_AIRCRAFT_TYPE', 'View Aircraft Types', 2, 5, 0),
            (6, 'CREATE_AIRCRAFT_TYPE', 'Create an Aircraft Type', 2, 6, 0),
            (7, 'UPDATE_AIRCRAFT_TYPE', 'Edit an Aircraft Type', 2, 7, 0),
            (8, 'DELETE_AIRCRAFT_TYPE', 'Delete an Aircraft Type', 2, 8, 0),
            (9, 'VIEW_AIRCRAFT', 'View Aircraft', 3, 9, 0),
            (10, 'CREATE_AIRCRAFT', 'Add an Aircraft', 3, 10, 0),
            (11, 'UPDATE_AIRCRAFT', 'Edit an Aircraft', 3, 11, 0),
            (12, 'DELETE_AIRCRAFT', 'Delete an Aircraft', 3, 12, 0),
            (13, 'VIEW_AIRPORT', 'View Airports', 4, 13, 0),
            (14, 'CREATE_AIRPORT', 'Add an Airport', 4, 14, 0),
            (15, 'DELETE_AIRPORT', 'Delete an Airport', 4, 15, 0),
            (16, 'UPDATE_AIRPORT', 'Edit an Airport', 4, 16, 0),
            (17, 'VIEW_DCS_AIRPORT', 'View DCS Airport', 6, 17, 0),
            (18, 'UPDATE_DCS_AIRPORT', 'Edit a DCS Airport', 6, 18, 0),
            (19, 'DELETE_DCS_AIRPORT', 'Delete a DCS Airport', 6, 19, 0),
            (20, 'VIEW_CITY_PAIR', 'View City Pairs', 7, 20, 0),
            (21, 'CREATE_CITY_PAIR', 'Create a New City Pair', 7, 21, 0),
            (22, 'UPDATE_CITY_PAIR', 'Update a City Pair', 7, 22, 0),
            (23, 'DELETE_CITY_PAIR', 'Delete a City Pair', 7, 23, 0),
            (24, 'VIEW_PRBD', 'View PRBDs', 8, 24, 0),
            (25, 'CREATE_PRBD', 'Create a PRBD', 8, 25, 0),
            (26, 'ACTIVATE_PRBD', 'Activate a PRBD', 8, 26, 0),
            (27, 'DEACTIVATE_PRBD', 'Deactivate a PRBD', 8, 27, 0),
            (28, 'UPDATE_PRBD', 'Edit a PRBD', 8, 28, 0),
            (29, 'DELETE_PRBD', 'Delete a PRBD', 8, 29, 0),
            (30, 'VIEW_OFFER_TEMPLATE', 'View Offer Templates', 9, 30, 0),
            (31, 'CREATE_OFFER_TEMPLATE', 'Create an Offer Template', 9, 31, 0),
            (32, 'UPDATE_OFFER_TEMPLATE', 'Edit an Offer Template', 9, 32, 0),
            (33, 'CREATE_FLIGHT_SCHEDULE', 'Add a Flight Schedule', 10, 33, 0),
            (34, 'VIEW_CONNECTING_FLIGHT', 'View Connecting Flights', 11, 34, 0),
            (35, 'PUBLISH_CONNECTING_FLIGHT', 'Publish Connecting Flights', 11, 35, 0),
            (36, 'UNPUBLISH_CONNECTING_FLIGHT', 'Un publish Connecting Flights', 11, 36, 0),
            (37, 'VIEW_GROUP', 'View Group Information', 102, 37, 0),
            (38, 'CREATE_GROUP', 'Create a New Group', 102, 38, 0),
            (39, 'UPDATE_GROUP', 'Update Group Information', 102, 39, 0),
            (40, 'UPDATE_GROUP_ACTION_PERMISSION', 'Update Action Permission of a Group', 102, 40, 0),
            (41, 'VIEW_USER', 'View User Information', 103, 41, 0),
            (42, 'CREATE_USER', 'Create a New User', 103, 42, 0),
            (43, 'UPDATE_USER', 'Update User Information', 103, 43, 0),
            (44, 'UPDATE_USER_GROUP', 'Update Assigned Group of a User', 103, 44, 0),
            (45, 'UPDATE_USER_ADDITIONAL_ACTIONS', 'Update Additional Actions of a User', 103, 45, 0),
            (46, 'UPDATE_USER_ACTIVE_STATUS', 'Activate/Deactivate a User', 103, 46, 0),
            (47, 'VIEW_PASSWORD_POLICY', 'View Password Policies', 108, 47, 0),
            (48, 'UPDATE_PASSWORD_POLICY', 'Update Password Policies', 108, 48, 0))
on conflict do nothing;

-- Give 'ADMIN' group to all action permission
insert into adm_group_wise_action_mapping(group_id, action_id)
select 1, id from adm_action on conflict do nothing ;

commit;