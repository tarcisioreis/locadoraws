-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 29-Mar-2019 às 00:53
-- Versão do servidor: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `locadora`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `devolucao`
--

CREATE TABLE `devolucao` (
  `id` bigint(20) NOT NULL,
  `idFilme` bigint(20) NOT NULL,
  `dataEntrega` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Devolução de filme';

--
-- Extraindo dados da tabela `devolucao`
--

INSERT INTO `devolucao` (`id`, `idFilme`, `dataEntrega`) VALUES
(20, 1, '2019-03-08');

-- --------------------------------------------------------

--
-- Estrutura da tabela `estoque`
--

CREATE TABLE `estoque` (
  `id` bigint(20) NOT NULL,
  `idFilme` bigint(20) NOT NULL,
  `quantidade` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Estoque de filmes';

--
-- Extraindo dados da tabela `estoque`
--

INSERT INTO `estoque` (`id`, `idFilme`, `quantidade`) VALUES
(1, 1, 5),
(2, 2, 20),
(4, 3, 10);

-- --------------------------------------------------------

--
-- Estrutura da tabela `filme`
--

CREATE TABLE `filme` (
  `id` bigint(20) NOT NULL,
  `titulo` varchar(80) NOT NULL,
  `diretor` varchar(80) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Filmes de cinema';

--
-- Extraindo dados da tabela `filme`
--

INSERT INTO `filme` (`id`, `titulo`, `diretor`) VALUES
(1, 'Capitã Marvel', 'Anna Boden, Ryan Fleck'),
(2, 'Shazam', 'David F. Sandberg'),
(3, 'Poderoso chefão', 'Teste'),
(4, 'Capitão Marvel', 'Teste');

-- --------------------------------------------------------

--
-- Estrutura da tabela `locacao`
--

CREATE TABLE `locacao` (
  `id` bigint(20) NOT NULL,
  `idFilme` bigint(20) NOT NULL,
  `idUsuario` bigint(20) NOT NULL,
  `dataRetirada` date NOT NULL,
  `dataEntrega` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Locação de Filmes';

--
-- Extraindo dados da tabela `locacao`
--

INSERT INTO `locacao` (`id`, `idFilme`, `idUsuario`, `dataRetirada`, `dataEntrega`) VALUES
(20, 1, 1, '2019-03-01', '2019-03-08');

-- --------------------------------------------------------

--
-- Estrutura da tabela `usuario`
--

CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL COMMENT 'ID do usuário',
  `nome` varchar(80) NOT NULL COMMENT 'Nome do usuário',
  `email` varchar(255) NOT NULL COMMENT 'E-mail do usuário',
  `senha` varchar(32) NOT NULL COMMENT 'Senha do usuário',
  `telefone` varchar(15) DEFAULT NULL,
  `status` int(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COMMENT='Usuários do sistema';

--
-- Extraindo dados da tabela `usuario`
--

INSERT INTO `usuario` (`id`, `nome`, `email`, `senha`, `telefone`, `status`) VALUES
(1, 'Tarcisio Machado dos Reis', 'tarcisio.reis.ti@gmail.com', '06e57a7dc40c9873382ae367f5ecfa26', '051 984904355', 0),
(6, 'Teste', 'teste@teste.com.br', '123', '51 984904355', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `devolucao`
--
ALTER TABLE `devolucao`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `estoque`
--
ALTER TABLE `estoque`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `EstoqueFilme_cfk` (`idFilme`) USING BTREE;

--
-- Indexes for table `filme`
--
ALTER TABLE `filme`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `locacao`
--
ALTER TABLE `locacao`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IX_LocacaoUsuario` (`idUsuario`),
  ADD KEY `IX_LocacaoFilme` (`idFilme`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ix_email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `devolucao`
--
ALTER TABLE `devolucao`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `estoque`
--
ALTER TABLE `estoque`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `filme`
--
ALTER TABLE `filme`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `locacao`
--
ALTER TABLE `locacao`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
--
-- AUTO_INCREMENT for table `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID do usuário', AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
