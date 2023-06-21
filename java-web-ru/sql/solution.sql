select first_name, birthday
from users
where birthday > '1999-10-23'
order by first_name
fetch first 3 rows only