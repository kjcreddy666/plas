<template>
    <div class="row mt-4">
      <div class="col-12">
        <div class="card mb-4">
          <div class="card-body">
            <h5><i class="bi bi-calendar"></i> Repayment Schedule</h5>
            <table class="table table-sm mt-3">
              <thead class="table-light">
                <tr>
                  <th>#</th>
                  <th>Month</th>
                  <th>Principal</th>
                  <th>Interest</th>
                  <th>Total Payment</th>
                  <th>Balance</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(entry, i) in schedule" :key="entry.id">
                  <td>{{ i + 1 }}</td>
                  <td>Month {{ entry.month }}</td>
                  <td>${{ entry.principalAmount.toFixed(2) }}</td>
                  <td>${{ entry.interestAmount.toFixed(2) }}</td>
                  <td>${{ entry.totalAmount.toFixed(2) }}</td>
                  <td>${{ entry.outstandingBalance.toFixed(2) }}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  defineProps(['schedule']);
  </script>
  