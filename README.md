# SuleimanovPracticeTask1

Описание задачи:
Необходимо реализовать консольное CRUD приложение, которое имеет следующие сущности:
Customer
Specialty
Account
AccountStatus (enum ACTIVE, BANNED, DELETED)
Customer-> Set<Specialty> specialties+ Account account
Account -> AccountStatus
В качестве хранилища данных необходимо использовать текстовые файлы:
customers.txt, specialties.txt, accounts.txt
Пользователь в консоли должен иметь возможность создания, получения, редактирования и удаления данных
Слои:
model - POJO класы
repository - классы, реализующие доступ к текстовым файлам
controller - обработка запросов от пользователя
view - все данные, необходимые для работы с консолью
Например: Customer, CustomerRepository, CustomerController, CustomerView и т.д.
Для репозиторного слоя желательно использовать базовый интерфейс:
interface GenericRepository<T,ID>
interface CustomerRepository extends GenericRepository<Customer, Long>
class JavaIOCustomerRepositoryImpl implements CustomerRepository
После запуска программы выйдет сообщение:
"Вы находитесь в приложении, которое может показывать и редактировать всю информацию о клиентах."
Введите в консоль номер действия:
1. Посмотреть/отредактировать данные клиентов.
2. Добавить клиента/Удалить клиента."
После этого нужно вводить в консоль варианты действий и нажимать на ENTER.
В дальнейшем возможные действия будут подробно описаны в консоли.
