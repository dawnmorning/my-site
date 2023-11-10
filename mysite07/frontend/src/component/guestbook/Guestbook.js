import React, { useEffect, useState } from "react";
import { MySiteLayout } from "../../layout";
import writeform from "../../assets/scss/component/guestbook/WriteForm.scss";
import gb from "../../assets/scss/component/guestbook/Guestbook.scss";
import msg from "../../assets/scss/component/guestbook/Message.scss";
import msglist from "../../assets/scss/component/guestbook/MessageList.scss";
import { deleteContents, getContents, postContents } from "../../api/guestbook";
function Guestbook(props) {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [contents, setContents] = useState("");
  const [articles, setArticles] = useState([]);
  const [isDelete, setIsDelete] = useState(false);
  const [selectedNo, setSelectedNo] = useState(null);
  const on이름 = (e) => {
    setName(e.target.value);
  };
  const on비밀번호 = (e) => {
    setPassword(e.target.value);
  };
  const on내용 = (e) => {
    setContents(e.target.value);
  };
  const on방명록쓰기 = async (e) => {
    e.preventDefault();
    const response = await postContents(name, password, contents);
    handle방명록조회();
    console.log(response);
  };
  const on비밀번호확인 = (e) => {
    setConfirmPassword(e.target.value);
  };
  const handle방명록조회 = async () => {
    const res = await getContents();
    setArticles(res);
    console.log(articles);
  };
  const handle삭제모달열기 = (no) => {
    setSelectedNo(no); // 삭제할 게시글 번호 설정
    setIsDelete(true);
  };
  const handle게시글삭제 = async () => {
    if (selectedNo) {
      await deleteContents(selectedNo, confirmPassword);
      // 삭제 후 방명록 재조회
      handle방명록조회();
      setIsDelete(false); // 모달 닫기
      setConfirmPassword("");
    }
  };
  useEffect(() => {
    handle방명록조회();
  }, []);
  return (
    <MySiteLayout>
      <div>
        <div className={gb.Guestbook}>
          <h2>방명록</h2>
        </div>
        <form onSubmit={on방명록쓰기} className={writeform.WriteForm}>
          <div className={writeform.userInfo}>
            <label htmlFor="name-input">이름</label>
            <input type="text" id="name-input" onChange={on이름} value={name} />
            <label htmlFor="password-input">비밀번호</label>
            <input
              type="password"
              id="password-input"
              onChange={on비밀번호}
              value={password}
            />
          </div>
          <div>
            <textarea
              name=""
              id=""
              cols="30"
              rows="10"
              placeholder="방명록을 작성해보세요."
              onChange={on내용}
              value={contents}
            ></textarea>
          </div>
          <input type="submit" />
        </form>
      </div>
      <div>
        {articles.map((article) => (
          <div className={msg.Message} key={article.no}>
            <strong>{article.name}</strong>
            <p>{article.contents}</p>
            <button onClick={() => handle삭제모달열기(article.no)}></button>
          </div>
        ))}
        <div>
          {isDelete && (
            <div className={msglist.ModalBackdrop}>
              <div className={msglist.ModalContent}>
                <div className={msglist.MessageListContainer}>
                  <form action="" className={msglist.DeleteForm}>
                    <label htmlFor="">비밀번호</label>
                    <input
                      type="password"
                      value={confirmPassword}
                      onChange={on비밀번호확인}
                    />
                    <button type="button" onClick={handle게시글삭제}>
                      삭제
                    </button>
                  </form>
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </MySiteLayout>
  );
}

export default Guestbook;
