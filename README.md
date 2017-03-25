# 従業員情報管理システム

# 環境設定
## IDEをインストールする
STS(Spring Tools Suite)をインストールする。

## MVC
Controllerを使って静的なHTMLを表示する。

### controlerを作成する
* javaファイルのパッケージ名は任意
* ControllerであることをSpringに通知(@Controller)
* URLにマッピング(RequestMapping)
 * http://localhost:8080/employees
* DI
 * Serviceクラスを自動生成する(@Autowired)
* ブラウザからのGETメソッド呼び出しでlistEmployeesが呼ばれる
 * http://localhost:8080/employees
* 返値のHTMLが表示される
 * static/main/resource以下にHTMLファイルを作成する
 * listEmployees.html

```
jp.smaphonia.app.employee.EmployeesController.java

@Controller
@RequestMapping("employees")
public class EmployeesController {

	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(method=RequestMethod.GET)
	String listEmployees(Model model) {
		List<Employee> employees = employeeService.findAllEmployee();
		model.addAttribute("employees", employees);
		return "employee/listEmployees";
	}
    // ...
}
```

## Templateエンジン()を使う
* modelに表示するデータ(従業員データ(Employ))を格納する

```
<tr th:each="employee: ${employees}">
<td th:text="${employee.id}"></td>
```

## JPAを使う
* DBを作成する
* H2を使う設定を記述する
 * src/main/resources/application.properties
```
spring.jpa.database=H2
spring.datasource.url=jdbc:h2:~/ems
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.format_sql=true
spring.datasource.sql-script-encoding=UTF-8
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```
* テーブルは自動生成できる
 * SQLファイル(src/main/resources/data.sql)を置く

# ソースコード
https://github.com/unokun/ems
