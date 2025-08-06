<template>
  <div class="d-flex justify-content-center mt-4 sticky-top">
    <nav
      class="navbar navbar-expand-lg bg-body-tertiary py-2 px-4 rounded-4 shadow"
      style="width: 80%; max-width: 1200px"
    >
      <div class="container-fluid">
        <router-link class="navbar-brand fs-3" to="/"><strong>LoanFlow</strong></router-link>

        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse justify-content-between" id="navbarNav">
          <!-- Centered Nav Items -->
          <ul class="navbar-nav fs-5 mx-auto">
            <li
              v-for="(item, index) in filteredNavItems"
              :key="index"
              class="nav-item mx-3"
            >
              <router-link class="nav-link" :to="item.to" exact-active-class="active">
                {{ item.label }}
              </router-link>
            </li>
          </ul>

          <!-- Right-side Auth/Profile Dropdown -->
          <div class="d-flex align-items-center">
            <div v-if="isAuthenticated" class="dropdown">
              <a
                class="nav-link dropdown-toggle"
                href="#"
                role="button"
                id="profileDropdown"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <i class="bi bi-person-circle fs-4"></i>
              </a>
              <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="profileDropdown">
                <li>
                  <router-link class="dropdown-item" to="/profile">Profile</router-link>
                </li>
                <li>
                  <a class="dropdown-item" href="#" @click.prevent="logout">Logout</a>
                </li>
              </ul>
            </div>
            <div v-else>
            <router-link to="/auth?tab=login" class="btn btn-outline-primary me-2">
              Sign In
            </router-link>
            <router-link to="/auth?tab=register" class="btn btn-outline-primary">
              Sign Up
            </router-link>
          </div>

          </div>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: "AppNavbar",
  data() {
    return {
      isAuthenticated: false,
      role: null,
      navItems: {
        guest: [
          { label: "Home", to: "/" },
          { label: "About Us", to: "#" },
          { label: "Contact Us", to: "#" },

        ],
        customer: [
          { label: "Home", to: "/" },
          { label: "EMI Calculator", to: "/emi-calculator" },
          { label: "Dashboard", to: "/dashboard" },
          { label: "Apply", to: "/apply" },
          { label: "Support", to: "/support-ticket" },
        ],
        admin: [
          { label: "Home", to: "/" },
          { label: "Dashboard", to: "/dashboard" },
        ],
      },
    };
  },
  computed: {
    filteredNavItems() {
      if (!this.isAuthenticated) return this.navItems.guest;
      if (this.role === "CUSTOMER") return this.navItems.customer;
      if (this.role === "ADMIN") return this.navItems.admin;
      return this.navItems.guest;
    },
  },
  methods: {
    logout() {
      localStorage.clear();
      this.isAuthenticated = false;
      this.role = null;
      this.$router.push("/");
    },
  },
  mounted() {
    const token = localStorage.getItem("userToken");
    const userId = localStorage.getItem("userId");
    const email = localStorage.getItem("email");
    const role = localStorage.getItem("userRole");

    if (token && userId && role) {
      this.isAuthenticated = true;
      this.role = role.toUpperCase();
    }
  },
};
</script>

<style scoped>
.nav-link.active {
  font-weight: bold;
  color: #0d6efd !important;
}
</style>
