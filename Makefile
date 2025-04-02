SERVER=demo-backend
DATABASE=db-demo

.PHONY: help start stop restart

help:
	@echo "Available targets:"
	@echo "  make help           - Show this help message"
	@echo "  make start          - Start both backend servers using Docker"
	@echo "  make start-back     - Start only backend server using Docker"
	@echo "  make stop           - Stop all Docker containers"
	@echo "  make restart        - Restart all Docker containers"

start-back:
	@echo "Starting backend server..."
	@if [ ! -z "$(SERVER)" ]; then \
		docker stop $(SERVER); \
		docker start $(DATABASE); \
		docker start $(SERVER); \
		make serve; \
	fi

start:
	@echo "Starting both servers..."
	@if [ ! -z "$(SERVER)" ]; then make start-back; fi

stop:
	@echo "Stopping all servers..."
	@if [ ! -z "$(SERVER)" ]; then docker stop $(SERVER); fi
	@if [ ! -z "$(DATABASE)" ]; then docker stop $(DATABASE); fi

restart: stop start
