<template>
  <div class="card p-4 mx-auto w-75">
    <h4>Loan Details</h4>
    <div class="row mb-3">
      <div class="col">
        <label>Loan Amount Requested *</label>
        <input v-model="localForm.amount" type="number" step="1000" class="form-control" required placeholder="Enter loan amount (min 10,000)" @input="validateLoanAmount"/>
        <small v-if="loanAmountError" class="text-danger">{{ loanAmountError }}</small>
      </div>
      <div class="col">
        <label>Loan Term (Months) *</label>
        <select v-model="localForm.tenureMonths" class="form-select" @change="validateLoanTerm">
          <option value="" disabled selected>-- Select Loan Term --</option>
          <option :value="12">12 months</option>
          <option :value="24">24 months</option>
          <option :value="36">36 months</option>
          <option :value="48">48 months</option>
        </select>
        <small v-if="loanTermError" class="text-danger">{{ loanTermError }}</small>
      </div>
    </div>
    <div class="mb-4">
      <label for="purpose">Purpose <span class="text-danger">*</span></label>
      <textarea
        v-model="form.purpose"
        id="purpose"
        class="form-control"
        placeholder="Enter your purpose"
        rows="3"
        required
        style="min-width: 400px; width: 100%;"
        @input="validateLoanPurpose"
      ></textarea>
      <small v-if="loanPurposeError" class="text-danger">{{ loanPurposeError }}</small>
    </div>
    <div class="d-flex justify-content-between mt-3">
      <button class="btn btn-outline-secondary" @click="$emit('prev')">Previous</button>
      <button class="btn btn-primary" @click="goNext">Next</button>
    </div>
  </div>
</template>

<script>
export default {
  props: ['form'],
  emits: ['next', 'prev', 'update:form'],
  data() {
    return {
      localForm: { ...this.form },
      loanAmountError: null,
      loanTermError: null,
      loanPurposeError: null  
    };
  },
  watch: {
    localForm: {
      handler(val) {
        this.$emit('update:form', val);
      },
      deep: true
    }
  },
    methods: {
      validateLoanAmount(){
        if (this.localForm.amount === null || this.localForm.amount === '') {
        this.loanAmountError = 'Loan amount is required.'; 
        } else if (this.localForm.amount < 10000) {
        this.loanAmountError = 'Loan amount must be at least 10,000.';
        } else if (this.localForm.amount % 1000 !== 0) {
        this.loanAmountError = "Loan amount must be a multiple of 1,000.";
        } else if( this.localForm.amount > 5000000) {
          this.loanAmountError = 'Loan amount cannot exceed 50,00,000.';
        } else {
          this.loanAmountError = null;
        }
      },
      validateLoanTerm() {
        if (!this.localForm.tenureMonths) {
          this.loanTermError = 'Loan term is required.';
        return false;
      } 
        this.loanTermError = null;
        return true;
      },
      validateLoanPurpose() {
        if (!this.localForm.purpose) {
          this.loanPurposeError = 'Loan purpose is required.';
          return false;
        } 
        this.loanPurposeError = null;
        return true;
      },
      goNext() {
        this.validateLoanAmount();
        const validLoanTerm = this.validateLoanTerm();
        const validLoanPurpose = this.validateLoanPurpose();
        if (!this.loanAmountError && validLoanTerm && validLoanPurpose) {
          this.$emit('next');
        }
      }
    },
  }
</script>  