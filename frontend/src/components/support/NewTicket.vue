<template>
  <div >
    <!-- Success Overlay Popup -->
    <div
      v-if="showToast"
      class="position-fixed top-0 start-0 w-100 h-100 d-flex justify-content-center align-items-center"
      style="background: rgba(0,0,0,0.3); backdrop-filter: blur(7px); z-index: 2000;"
     >
    <div
      class="rounded-4 shadow p-5 mx-auto text-center d-flex flex-column align-items-center"
      style="min-width: 380px; min-height: 230px; width: 41vw; max-width: 515px; font-size: 1.3rem; background: #fff; color: #000;"
    >
      <span class="display-5 mb-3"><i class="bi bi-check2-circle"></i></span>
      <div class="fw-semibold mb-2">
        Ticket created successfully!
      </div>
    <div>Your issue will be resolved soon.</div>
    </div>
    </div>

    <!-- The Form Card -->
    <div class="border rounded-4 p-3">
      <h5 class="fw-bold mb-3">
        <i class="bi bi-plus-lg me-1"></i> Create New Support Ticket
      </h5>
      <form @submit.prevent="submitTicket">
        <div class="mb-3">
          <label class="form-label fw-semibold">Loan Application *</label>
          <select v-model="form.loanApplicationId" class="form-select" required>
            <option disabled value="">Select a loan</option>
            <option v-for="loan in loans" :key="loan.id" :value="loan.id">
            Loan # {{ loan.id }} - ₹{{ loan.amount }} - {{ loan.purpose }}
            </option>
          </select>
        </div>
        <div class="mb-3">
          <label class="form-label fw-semibold">Subject *</label>
          <input
            v-model="form.subject"
            type="text"
            class="form-control"
            placeholder="Brief description of your issue"
            maxlength="100"
            required
          />
        </div>
        <div class="mb-3">
          <label class="form-label fw-semibold">Description *</label>
          <textarea
            v-model="form.description"
            class="form-control"
            rows="4"
            placeholder="Please provide detailed information..."
            maxlength="1000"
            required
          ></textarea>
        </div>
        <div class="d-flex gap-2 mt-3">
          <button type="reset" class="btn btn-outline-dark">Clear Form</button>
          <button type="submit" class="btn btn-primary">Submit Ticket</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useLoan } from '@/composables/useLoan'
import { useTicket } from '@/composables/useTicket'

const userId = localStorage.getItem('userId')
const form = ref({
  userId: userId || '',
  loanApplicationId: '',
  subject: '',
  description: ''
})

const showToast = ref(false)

const { loans, fetchLoansByUser } = useLoan()
const { create } = useTicket()

onMounted(() => {
  if (userId) fetchLoansByUser(userId)
})

const closeToast = () => {
  showToast.value = false
  location.reload()
}

const submitTicket = async () => {
  if (!form.value.userId || !form.value.loanApplicationId) {
    alert('User ID and Loan Application ID are required.')
    return
  }

  const result = await create(form.value)
  if (result.isSuccess) {
    form.value.subject = ''
    form.value.description = ''
    form.value.loanApplicationId = ''
    showToast.value = true
    setTimeout(() => {
      closeToast()
    }, 4000)
  }
}
</script>
