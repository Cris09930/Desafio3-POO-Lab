-- Base de datos: `flotabdd`
--
Create database if not exists `flotabdd` default character set latin1 collate latin1_swedish_ci;
USE `flotabdd`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `conductor`
--

CREATE TABLE `conductor` (
  `dui` varchar(10) NOT NULL,
  `nombre_completo` varchar(100) NOT NULL,
  `edad` int(3) NOT NULL,
  `sexo` char(1) NOT NULL,
  `licencia_vigente` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `conductor`
--

INSERT INTO `conductor` (`dui`, `nombre_completo`, `edad`, `sexo`, `licencia_vigente`) VALUES
('12345678-9', 'Guido', 20, 'M', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_vehiculo`
--

CREATE TABLE `tipo_vehiculo` (
  `id_tipo` int(11) NOT NULL,
  `nombre_tipo` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `tipo_vehiculo`
--

INSERT INTO `tipo_vehiculo` (`id_tipo`, `nombre_tipo`) VALUES
(1, 'Moto'),
(2, 'Camion');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `id_vehiculo` int(11) NOT NULL,
  `id_tipo` int(11) NOT NULL,
  `marca` varchar(50) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `anio` int(4) NOT NULL,
  `dato_especifico` double NOT NULL,
  `estado_mantenimiento` varchar(30) DEFAULT 'Al día'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`id_vehiculo`, `id_tipo`, `marca`, `modelo`, `anio`, `dato_especifico`, `estado_mantenimiento`) VALUES
(6, 1, 'Yamaha', 'chi222', 2021, 8, 'Al día'),
(7, 2, 'Toyota', 'BIG-3230', 2005, 500, 'Requiere revisión'),
(8, 1, 'JADJAJSD', 'adasd211', 2017, 29, 'Al día');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `viaje`
--

CREATE TABLE IF NOT EXISTS `viaje` (
    `id_viaje` INT NOT NULL AUTO_INCREMENT,
    `dui_conductor` VARCHAR(10) NOT NULL,
    `id_vehiculo` INT NOT NULL,
    `distancia_km` DECIMAL(10,2) NOT NULL,
    `costo` DECIMAL(10,2) NOT NULL,
    `fecha_viaje` DATE NOT NULL,
    PRIMARY KEY (`id_viaje`),
    FOREIGN KEY (`dui_conductor`) REFERENCES `conductor`(`dui`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`id_vehiculo`) REFERENCES `vehiculo`(`id_vehiculo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `conductor`
--
ALTER TABLE `conductor`
  ADD PRIMARY KEY (`dui`);

--
-- Indices de la tabla `tipo_vehiculo`
--
ALTER TABLE `tipo_vehiculo`
  ADD PRIMARY KEY (`id_tipo`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`id_vehiculo`),
  ADD KEY `id_tipo` (`id_tipo`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tipo_vehiculo`
--
ALTER TABLE `tipo_vehiculo`
  MODIFY `id_tipo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  MODIFY `id_vehiculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD CONSTRAINT `vehiculo_ibfk_1` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_vehiculo` (`id_tipo`);

COMMIT;