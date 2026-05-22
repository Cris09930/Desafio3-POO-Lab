<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transportes Don Bosco | Inicio</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="index-page">
<div class="container py-5">
    <div class="hero-card p-5 mb-5 text-center">
        <h1 class="display-3 fw-bold text-primary">🚛 Transportes Don Bosco</h1>
        <p class="lead text-muted">Sistema integral de gestión de flota, conductores y viajes</p>
        <hr class="my-4">
        <p class="text-secondary">Seleccione una opción del menú para comenzar</p>
    </div>

    <div class="row g-4">
        <div class="col-md-3">
            <a href="ConductorServlet?accion=listar" class="menu-card text-center">
                <div class="menu-icon">👨‍✈️</div>
                <h4 class="text-primary">Conductores</h4>
                <p class="text-muted small">Gestionar personal</p>
            </a>
        </div>
        <div class="col-md-3">
            <a href="VehiculoServlet?accion=listar" class="menu-card text-center">
                <div class="menu-icon">🚐</div>
                <h4 class="text-success">Vehículos</h4>
                <p class="text-muted small">Administrar flota</p>
            </a>
        </div>
        <div class="col-md-3">
            <a href="ViajeServlet?accion=nuevo" class="menu-card text-center">
                <div class="menu-icon">🗺️</div>
                <h4 class="text-info">Registrar Viaje</h4>
                <p class="text-muted small">Nueva ruta</p>
            </a>
        </div>
        <div class="col-md-3">
            <a href="ReporteServlet" class="menu-card text-center">
                <div class="menu-icon">📊</div>
                <h4 class="text-warning">Reporte Final</h4>
                <p class="text-muted small">Estadísticas</p>
            </a>
        </div>
    </div>

    <div class="row mt-5">
        <div class="col-md-6 mx-auto">
            <div class="card bg-dark text-white text-center">
                <div class="card-body">
                    <h5>📅 Sistema desarrollado para Transportes Don Bosco</h5>
                    <small>Gestión eficiente y segura</small>
                </div>
            </div>
        </div>
    </div>
</div>
<footer class="footer-custom">
    <p>&copy; 2026 Transportes Don Bosco - Todos los derechos reservados</p>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
