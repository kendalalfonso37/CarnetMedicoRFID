-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-11-2018 a las 14:41:22
-- Versión del servidor: 10.1.13-MariaDB
-- Versión de PHP: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `arqui2018`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `consulta`
--

CREATE TABLE `consulta` (
  `idConsulta` int(11) NOT NULL,
  `idPaciente` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `descripcion` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `consulta`
--

INSERT INTO `consulta` (`idConsulta`, `idPaciente`, `fecha`, `hora`, `descripcion`) VALUES
(1, 1, '2016-01-05', '07:00:00', 'Dolor de cabeza'),
(2, 1, '2016-03-02', '09:00:00', 'Dolor de cuerpo'),
(3, 1, '2017-09-05', '14:00:00', 'Dolor de oido'),
(4, 1, '2017-12-05', '20:00:00', 'Mareos'),
(5, 2, '2014-01-05', '07:00:00', 'Fiebre'),
(6, 2, '2016-03-02', '09:00:00', 'Zica'),
(7, 2, '2017-09-05', '14:00:00', 'Infeccion en la garganta'),
(8, 2, '2018-12-05', '20:00:00', 'Gripe'),
(9, 3, '2011-01-05', '07:00:00', 'Dengue'),
(10, 3, '2012-03-02', '09:00:00', 'Tos'),
(11, 3, '2013-09-05', '14:00:00', 'Infeccion en las vias Urinarias'),
(12, 3, '2014-12-05', '20:00:00', 'Gripe'),
(13, 4, '2014-01-05', '07:00:00', 'Fiebre'),
(14, 4, '2016-03-02', '09:00:00', 'Zica'),
(15, 4, '2017-09-05', '14:00:00', 'Dolor de estomago'),
(16, 4, '2018-12-05', '20:00:00', 'Gripe');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paciente`
--

CREATE TABLE `paciente` (
  `idPaciente` int(11) NOT NULL,
  `codigoPaciente` varchar(45) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `edad` int(11) NOT NULL,
  `genero` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `paciente`
--

INSERT INTO `paciente` (`idPaciente`, `codigoPaciente`, `nombre`, `apellido`, `edad`, `genero`) VALUES
(1, '4DDB40D5', 'Edwin', 'Diaz', 25, 'Masculino'),
(2, 'B0A16025', 'Oscar', 'Mayen', 25, 'Masculino'),
(3, '865EF674', 'Kendal', 'Sosa', 25, 'Masculino'),
(4, '0B6155D3', 'Elmer', 'Huiza', 25, 'Masculino');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `consulta`
--
ALTER TABLE `consulta`
  ADD PRIMARY KEY (`idConsulta`),
  ADD KEY `fk_Consulta_paciente_idx` (`idPaciente`);

--
-- Indices de la tabla `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`idPaciente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `consulta`
--
ALTER TABLE `consulta`
  MODIFY `idConsulta` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT de la tabla `paciente`
--
ALTER TABLE `paciente`
  MODIFY `idPaciente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `consulta`
--
ALTER TABLE `consulta`
  ADD CONSTRAINT `fk_Consulta_paciente` FOREIGN KEY (`idPaciente`) REFERENCES `paciente` (`idPaciente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
