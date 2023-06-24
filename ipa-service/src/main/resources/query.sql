SELECT
    ipa_module.id moduleId,
    ipa_module.name moduleName,
    json_agg(
            json_build_object(
                    'menuId', ipa_menu.id,
                    'menuName', ipa_menu.name,
                    'actions', (
                        SELECT json_agg(json_build_object('id', a.id, 'name', a.name))
                        FROM ipa_admin_action AS a
                        WHERE a.menu_id = ipa_menu.id
                    )
                )
        ) AS moduleMenuList
FROM
    ipa_admin_module AS ipa_module
        JOIN
    ipa_admin_menu AS ipa_menu ON ipa_module.id = ipa_menu.module_id

GROUP BY
    ipa_module.id,
    ipa_module.name;