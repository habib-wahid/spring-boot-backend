-- Authored by Junaid Khan Pathan

INSERT INTO usba.adm_email_data (version, email_type, subject, body)
VALUES (1, 'OTP_2FA', 'PSS - Login OTP', '<html>
    <head>
        <style>
            body {
                font-family: ''Segoe UI'', Tahoma, Geneva, Verdana, sans-serif;
            }
            table {
                text-align: left;
                padding-top: 10px;
            }
            th {
                width: 200px;
                padding-left: 10px;
                background-clip: content-box;
                background-color: #EEEEEE;
                font-weight: normal;
            }
            td {
                padding: 5px;
                width: 300px;
                border: 1px solid black;
            }
            .fine {
                font-weight: bold;
                color: #FF0000;
            }
            .logo {
                float: left;
                display: block;
                margin-top: -15px;
            }
            .title {
                display: block;
            }
            .logo-name {
                color: #FFFFFF;
                background-color: #2AA3D9;
                vertical-align: middle;
                padding: 10px;
                margin-top: 20px;
                height: 20px;
                width: 400px;
            }
            .logo-bar {
                background-color: #005D91;
                width: 420px;
                height: 20px;
                margin-top: -22px;
                margin-bottom: 30px;
            }
        </style>
    </head>
    <body>
        <div class=''logo''>
            <svg version=''1.1'' width=''105px'' height=''85px'' viewBox=''-0.5 -0.5 105 85''>
                <defs/>
                <g>
                    <path d=''M 25.51 71.39 L 45.51 31.2 L 97.04 31.2 L 77.04 71.39 Z'' fill=''#000000'' stroke=''none'' transform=''translate(2,3)rotate(-15,61.27,51.29)'' opacity=''0.25''/>
                    <path d=''M 25.51 71.39 L 45.51 31.2 L 97.04 31.2 L 77.04 71.39 Z'' fill=''#e6e6e6'' stroke=''none'' transform=''rotate(-15,61.27,51.29)'' pointer-events=''all''/>
                    <path d=''M 15.51 60.24 L 35.51 20.05 L 87.04 20.05 L 67.04 60.24 Z'' fill=''#000000'' stroke=''none'' transform=''translate(2,3)rotate(-15,51.27,40.14)'' opacity=''0.25''/>
                    <path d=''M 15.51 60.24 L 35.51 20.05 L 87.04 20.05 L 67.04 60.24 Z'' fill=''#2aa3d9'' stroke=''none'' transform=''rotate(-15,51.27,40.14)'' pointer-events=''all''/>
                    <path d=''M 4.39 49.08 L 24.39 8.89 L 75.92 8.89 L 55.92 49.08 Z'' fill=''#000000'' stroke=''none'' transform=''translate(2,3)rotate(-15,40.16,28.99)'' opacity=''0.25''/>
                    <path d=''M 4.39 49.08 L 24.39 8.89 L 75.92 8.89 L 55.92 49.08 Z'' fill=''#005d91'' stroke=''none'' transform=''rotate(-15,40.16,28.99)'' pointer-events=''all''/>
                </g>
            </svg>
        </div>
        <div class=''title''>
            <h4 class=''logo-name''>PSS - US Bangle Airlines</h4>
            <div class=''logo-bar''>&nbsp;</div>
        </div>

        <p>Dear Mr./Miss/Mrs. %s,</p>

        <p>Enter the 6-digit OTP code below to verify your identity to log into your account.</p>

        <p style="font-size: 24px;"><b>%s</b></p>

        <p>If you didn''t try to log into your account then please contact our customer support to secure your account.</p>

        <p>
        Yours sincerely,<br/>
        PSS - US Bangla Airlines
        </p>
    </body>
</html>');