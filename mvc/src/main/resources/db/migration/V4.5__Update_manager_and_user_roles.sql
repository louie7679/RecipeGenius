--Update the 'Manager' role's allowed resources
UPDATE roles
SET allowed_resource = '/user,/recipe,/ingredient'
WHERE name = 'Manager';

--Update the 'user' role's allowed resources
UPDATE roles
SET allowed_resource = '/user,/recipe,/ingredient'
WHERE name = 'user';
