<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Viaje</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }
        .card-viaje {
            border-radius: 1.25rem;
            overflow: hidden;
            border: none;
            box-shadow: 0 20px 40px rgba(0,0,0,0.2);
        }
        .card-header-viaje {
            background: linear-gradient(135deg, #0a2b5e 0%, #1e3a7a 100%);
            color: white;
            padding: 1.5rem;
            font-size: 1.5rem;
            font-weight: bold;
            text-align: center;
            border-bottom: 3px solid #c9a03d;
        }
        .btn-viaje-gradient {
            background: linear-gradient(135deg, #0a2b5e 0%, #c9a03d 100%);
            color: white;
            border: none;
            transition: all 0.3s ease;
            font-weight: bold;
            padding: 0.75rem;
        }
        .btn-viaje-gradient:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(0,0,0,0.2);
            color: white;
        }
        .alert-custom {
            border-radius: 0.75rem;
            padding: 1rem 1.25rem;
            margin-bottom: 1.25rem;
            display: flex;
            align-items: center;
            gap: 0.75rem;
        }
        .alert-error {
            background: linear-gradient(135deg, #f8d7da 0%, #fff5f5 100%);
            border-left: 5px solid #dc3545;
            color: #721c24;
        }
        .alert-success {
            background: linear-gradient(135deg, #d1e7dd 0%, #f0fff4 100%);
            border-left: 5px solid #198754;
            color: #0a3622;
        }
        .alert-warning {
            background: linear-gradient(135deg, #fff3cd 0%, #fffbeb 100%);
            border-left: 5px solid #ffc107;
            color: #856404;
        }
        .alert-icon {
            font-size: 1.5rem;
        }
        .alert-content {
            flex: 1;
            font-weight: 500;
        }
        .form-control:focus, .form-select:focus {
            border-color: #c9a03d;
            box-shadow: 0 0 0 0.2rem rgba(201, 160, 61, 0.25);
        }
        .form-label {
            font-weight: 600;
            color: #0a2b5e;
        }
    </style>
</head>
<body>
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8">

            <!-- MOSTRAR MENSAJE DE ÉXITO -->
            <c:if test="${not empty sessionScope.mensaje}">
                <div class="alert-custom alert-success">
                    <span class="alert-icon">✅</span>
                    <span class="alert-content">${sessionScope.mensaje}</span>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="mensaje" scope="session"/>
            </c:if>

            <!-- MOSTRAR MENSAJE DE ERROR (desde sesión) -->
            <c:if test="${not empty sessionScope.mensajeError}">
                <div class="alert-custom alert-error">
                    <span class="alert-icon">❌</span>
                    <span class="alert-content">${sessionScope.mensajeError}</span>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <c:remove var="mensajeError" scope="session"/>
            </c:if>

            <!-- ADVERTENCIA POR FALTA DE DATOS -->
            <c:if test="${empty conductores or empty vehiculos}">
                <div class="alert-custom alert-warning">
                    <span class="alert-icon">⚠️</span>
                    <span class="alert-content">
                        No hay conductores con licencia vigente o vehículos en estado "Al día".
                    </span>
                </div>
            </c:if>

            <!-- TARJETA DEL FORMULARIO -->
            <div class="card card-viaje">
                <div class="card-header-viaje">
                    🗺️ Registro de Nuevo Viaje
                </div>
                <div class="card-body p-4">
                    <form action="ViajeServlet" method="post" id="formViaje">

                        <div class="mb-3">
                            <label class="form-label">👨‍✈️ Conductor</label>
                            <select name="duiConductor" class="form-select" required>
                                <option value="">-- Seleccione un conductor --</option>
                                <c:forEach var="c" items="${conductores}">
                                    <option value="${c.dui}">${c.nombreCompleto} (${c.dui})</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">🚐 Vehículo</label>
                            <select name="idVehiculo" class="form-select" required>
                                <option value="">-- Seleccione un vehículo --</option>
                                <c:forEach var="v" items="${vehiculos}">
                                    <option value="${v.idVehiculo}">
                                            ${v.marca} ${v.modelo} (${v.nombreTipo})
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-4">
                            <label class="form-label">📏 Distancia (km)</label>
                            <input type="number" step="any" name="distanciaKm" class="form-control"
                                   placeholder="Ej: 120.5" required min="0.1">
                            <div class="form-text text-muted mt-1">
                                Distancia en kilómetros (valor positivo)
                            </div>
                        </div>

                        <div class="d-flex gap-3 mt-4">
                            <button type="submit" class="btn btn-viaje-gradient w-50">
                                💰 Calcular y Registrar
                            </button>
                            <a href="index.jsp" class="btn btn-outline-secondary w-50 text-center text-decoration-none py-2">
                                ← Cancelar
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
