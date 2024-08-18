Feature: User
    
    Scenario: ユーザー作成後に作成したユーザーが取得できるか確認
        Given RequestBodyの "username" に "user" を設定
        Given RequestBodyの "password" に "password" を設定
        When "/test" にPUTリクエストを送信
        Then ステータスコードが200であること
        And ResponseBodyの "$.user.id" がnullではないこと
        And ResponseBodyの "$.user.username" が "user" であること
        And ResponseBodyの "$.user.password" が "password" であること

        Given RequestParamの "id" に ResponseBodyの "$.user.id" を設定
        When "/test" にGETリクエストを送信
        Then ステータスコードが200であること
        And ResponseBodyの "$.user.id" がnullではないこと
        And ResponseBodyの "$.user.username" が "user" であること
        And ResponseBodyの "$.user.password" が "password" であること
    
    Scenario: 未作成のIDを指定した場合にnullが返却されるか確認
        Given RequestParamの "id" に "1" を設定
        When "/test" にGETリクエストを送信
        Then ステータスコードが200であること
        And ResponseBodyの "$.user" がnullであること