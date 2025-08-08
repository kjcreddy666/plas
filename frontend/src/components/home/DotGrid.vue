<template>
    <svg :width="size" :height="size">
      <g v-for="(row, rowIndex) in grid" :key="rowIndex">
        <circle
          v-for="(dot, colIndex) in row"
          :key="colIndex"
          :cx="colIndex * spacing"
          :cy="rowIndex * spacing"
          :r="dotRadius(rowIndex, colIndex)"
          :fill="dotColor(rowIndex, colIndex)"
        />
      </g>
      <rect
        :x="center - squareSize / Math.SQRT2"
        :y="center - squareSize / Math.SQRT2"
        :width="squareSize * Math.SQRT2"
        :height="squareSize * Math.SQRT2"
        :fill="overlayColor"
        :transform="'rotate(45 ' + center + ' ' + center + ')'"
        rx="12"
      />
    </svg>
  </template>
  
  <script setup>
  const rows = 20;
  const cols = 20;
  const spacing = 30;
  const size = rows * spacing;
  const center = size / 2;
  const squareSize = 150;
  
  const overlayColor = 'rgba(136, 114, 255, 0.2)';
  const baseColor = '#8872ff';
  
  const grid = Array.from({ length: rows }, () => Array(cols).fill(0));
  
  const dotRadius = (row, col) => {
    const dist = Math.hypot(row - rows / 2, col - cols / 2);
    return Math.max(1.5, 4 - dist * 0.2);
  };
  
  const dotColor = (row, col) => {
    const dist = Math.hypot(row - rows / 2, col - cols / 2);
    const alpha = Math.max(0.1, 1 - dist * 0.08);
    return `rgba(136, 114, 255, ${alpha.toFixed(2)})`;
  };
  </script>
  