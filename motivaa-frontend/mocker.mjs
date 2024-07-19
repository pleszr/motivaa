import { fileURLToPath } from 'url';
import { dirname } from 'path';
import axios from 'axios';
import swaggerUi from 'swagger-ui-express';
import jsonServer from 'json-server';
import bodyParser from 'body-parser';
import path from 'path';
import { v4 as uuidv4 } from 'uuid';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

const PORT = 3001;
const SWAGGER_URL = process.env.BACKEND_URL || 'http://localhost:8093/v3/api-docs'; // Use environment variable
const DB_FILE = path.resolve(__dirname, 'db.json');

function isValidRequestData(data, schema) {
  return !!data;
}

async function createRoutesFromSwagger(swaggerUrl) {
  try {
    const response = await axios.get(swaggerUrl);
    const swaggerDocument = response.data;

    const server = jsonServer.create();
    const router = jsonServer.router(DB_FILE);
    const middlewares = jsonServer.defaults();

    server.use(middlewares);
    server.use(bodyParser.json());

    for (const [path, pathDefinition] of Object.entries(swaggerDocument.paths)) {
      for (const [method, methodDefinition] of Object.entries(pathDefinition)) {
        const { responses, requestBody } = methodDefinition;

        server[method.toLowerCase()](
          path,
          async (req, res) => {
            try {
              if (method.toLowerCase() === 'post' && path.endsWith('/users') && responses['200']) {
                const requestSchema = requestBody?.content['application/json']?.schema;
                if (!requestSchema) {
                  console.error(`Schema not defined for ${method} ${path}`);
                  return res.status(500).json({
                    message: 'Internal Server Error'
                  });
                }

                if (!isValidRequestData(req.body, requestSchema)) {
                  return res.status(400).json({
                    message: 'Invalid request body'
                  });
                }

                const { firstName, lastName } = req.body;
                if (!firstName || !lastName) {
                  return res.status(400).json({
                    message: 'firstName and lastName are required fields'
                  });
                }

                const newUser = {
                  uuid: uuidv4(),
                  ...req.body,
                  status: 'active',
                  createdTimestamp: new Date().toISOString(),
                  lastLoginTimestamp: new Date().toISOString()
                };

                router.db.get('users').push(newUser).write();

                console.log(router.db.get('users').value());

                res.status(200).json({
                  message: 'User created successfully'
                });
              }
              if (method.toLowerCase() === 'post' && path.endsWith('/createHabit') && responses['200']) {
                const requestSchema = requestBody?.content['application/json']?.schema;
                if (!requestSchema) {
                  console.error(`Schema not defined for ${method} ${path}`);
                  return res.status(500).json({
                    message: 'Internal Server Error'
                  });
                }

                if (!isValidRequestData(req.body, requestSchema)) {
                  return res.status(400).json({
                    message: 'Invalid request body'
                  });
                }

                const { userUuid, name, recurringType } = req.body;
                if (!userUuid || !name || !recurringType) {
                  return res.status(400).json({
                    message: 'userUuid, name, and recurringType are required fields'
                  });
                }

                const newHabit = {
                  uuid: uuidv4(),
                  ...req.body,
                  createdTimestamp: new Date().toISOString()
                };

                router.db.get('habits').push(newHabit).write();

                console.log(router.db.get('habits').value());

                res.status(200).json({
                  message: 'Habit created successfully'
                });
              }

              if (method.toLowerCase() === 'get' && path.endsWith('/createHabit') && responses['200'] && responses['200'].content['application/json'].schema) {
                const responseData = router.db.get('habits').value();
                res.status(200).json(responseData);
              }

              if (method.toLowerCase() === 'get' && responses['200'] && responses['200'].content['application/json'].schema) {
                const { uuid } = req.query;
                if (uuid) {
                  const user = router.db.get('users').find({ uuid }).value();
                  if (user) {
                    res.status(200).json({
                      users: [user]
                    });
                  } else {
                    res.status(404).json({
                      message: 'User not found'
                    });
                  }
                } else {
                  const responseData = router.db.get('users').value();
                  res.status(200).json(responseData);
                }
              }
            } catch (error) {
              console.error('Error handling request:', error);
              res.status(500).send('Internal Server Error');
            }
          }
        );
      }
    }

    server.use('/api', router);

    server.use('/api-docs', async (req, res, next) => {
      try {
        swaggerUi.setup(swaggerDocument)(req, res, next);
      } catch (error) {
        res.status(500).send('Error rendering Swagger UI');
      }
    });

    server.listen(PORT, () => {
      console.log(`Mock API server is running on http://localhost:${PORT}`);
      console.log(`In order to use this mocker, please run motivaa-backend, Swagger UI available at http://localhost:8093/v3/api-docs`);
    });

  } catch (error) {
    console.error('Error fetching Swagger documentation:', error);
  }
}

createRoutesFromSwagger(SWAGGER_URL);







