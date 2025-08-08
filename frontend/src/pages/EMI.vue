<template>
  <div class="container py-5 min-vh-100">
    <IntroSection />

    <div class="row justify-content-center mt-4">
      <div class="col-md-6 mb-4">
        <LoanForm
          :amount="amount"
          :rate="rate"
          :tenure="tenure"
          @update:amount="val => amount = val"
          @update:rate="val => rate = val"
          @update:tenure="val => tenure = val"
          @calculate="calculateEMI"
        />
      </div>

      <div class="col-md-6">
        <ReadyToCalculate v-if="emi === null" />
        <EMIBreakdown
          v-else
          :emi="emi"
          :total="total"
          :interest="interest"
          :amount="amount"
        />
      </div>
    </div>

    <div class="row justify-content-center mt-4 container">
      <div class="col-md-10">
        <div class="card shadow-sm border-0">
          <div class="card-body">
            <h3 class="card-title">Understanding EMI Calculation</h3>

            <div class="mt-3">
              <h5>What is EMI?</h5>
              <p>
                Equated Monthly Installment (EMI) is a fixed payment amount made by a borrower to a lender at a specified date each calendar month.
              </p>
            </div>

            <div class="mt-3">
              <h5>How is it calculated?</h5>
              <p>
                EMI is calculated using the formula: <br />
                <strong>EMI = [P × R × (1 + R)<sup>N</sup>] / [(1 + R)<sup>N</sup> − 1]</strong><br />
                where <strong>P</strong> is the principal amount, <strong>R</strong> is the monthly interest rate, and <strong>N</strong> is the tenure in months.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import IntroSection from '../components/emi/EMIIntroSection.vue'
import LoanForm from '../components/emi/EMILoanForm.vue'
import EMIBreakdown from '../components/emi/EMIBreakdown.vue'
import ReadyToCalculate from '../components/emi/ReadyToCalculate.vue'

const router = useRouter()
const userId = ref(localStorage.getItem('userId'))
const token = ref(localStorage.getItem('userToken'))
const role = ref(localStorage.getItem('userRole'))

const amount = ref(100000)
const rate = ref(8.5)
const tenure = ref(24)

const emi = ref(null)
const total = ref(0)
const interest = ref(0)

const calculateEMI = () => {
  const P = amount.value
  const r = rate.value / 12 / 100
  const n = tenure.value

  const calculatedEMI = P * r * Math.pow(1 + r, n) / (Math.pow(1 + r, n) - 1)
  const calculatedTotal = calculatedEMI * n
  const calculatedInterest = calculatedTotal - P

  emi.value = Math.round(calculatedEMI)
  total.value = Math.round(calculatedTotal)
  interest.value = Math.round(calculatedInterest)
}
</script>
