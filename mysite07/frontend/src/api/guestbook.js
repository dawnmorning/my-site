import defaultAxios from "./instance";
// get
async function getContents() {
  try {
    const { data } = await defaultAxios.get(`/api/guestbook`);
    if (data.result === "success") {
      return data.data;
    }
  } catch (error) {
    console.log(error);
  }
}

// post
async function postContents(name, password, contents) {
  try {
    const { data } = await defaultAxios.post(`/api/guestbook`, {
      name,
      password,
      contents,
    });
    if (data.result === "success") {
      alert("방명록이 등록되었어요.");
      return data.data;
    } else {
      alert("오류가 발생했어요.");
      return "";
    }
  } catch (error) {
    console.log(error);
  }
}

// delete
async function deleteContents(no, password) {
  try {
    const { data } = await defaultAxios.delete(`/api/guestbook/${no}`, {
      params: {
        password: password,
      },
    });
    if (data.result === "success") {
      return data.data;
    } else {
      throw new Error(data.message || "삭제에 실패했습니다.");
    }
  } catch (error) {
    console.error("deleteContents Error:", error);
    throw error;
  }
}
export { getContents, postContents, deleteContents };
