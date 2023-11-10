import axios from "axios";

const defaultAxios = axios.create({
  headers: {
    "Content-Type": "application/json;charset=utf-8",
  },
});

export default defaultAxios;
