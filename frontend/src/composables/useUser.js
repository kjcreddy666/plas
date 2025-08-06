import {
    createUser,
    getAllUsers,
    getUserById,
    getUserByEmail,
    getUserByMobile,
    updateUser,
    deleteUser
  } from '@/services/userService';
  
  export const useUser = () => {
    const registerUser = async (userData) => {
      return await createUser(userData);
    };
  
    const fetchAllUsers = async () => {
      return await getAllUsers();
    };
  
    const fetchUserById = async (id) => {
      return await getUserById(id);
    };
  
    const fetchUserByEmail = async (email) => {
      return await getUserByEmail(email);
    };
  
    const fetchUserByMobile = async (mobile) => {
      return await getUserByMobile(mobile);
    };
  
    const modifyUser = async (id, updatedData) => {
      return await updateUser(id, updatedData);
    };
  
    const removeUser = async (id) => {
      return await deleteUser(id);
    };
  
    return {
      registerUser,
      fetchAllUsers,
      fetchUserById,
      fetchUserByEmail,
      fetchUserByMobile,
      modifyUser,
      removeUser
    };
  };
  