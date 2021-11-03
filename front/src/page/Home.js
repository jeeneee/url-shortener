import React, {useState} from "react";
import axios from "axios";

export default function Home() {
  const [url, setUrl] = useState("");
  const [message, setMessage] = useState("");
  const [shortUrl, setShortUrl] = useState("");

  function submitHandler(e) {
    e.preventDefault();
    if (validate(url) !== false) {
      setMessage("");
      createShortUrlApi().catch((err) => setMessage(err.message));
    } else {
      setMessage("CHECK YOUR URL");
    }
  }

  function createShortUrlApi() {
    return axios
      .post("https://api.urlshortener.shop", {
        longUrl: url,
      })
      .catch(() => {
            throw new Error("ERROR");
      })
      .then((res) => {
        setShortUrl(res.data);
      });
  }

  function validate(str) {
    const pattern = new RegExp("[https?://(www.)?a-zA-Z0-9@:%._+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_+.~#?&//=]*)");
    return pattern.test(str);
  }

  return (
    <section>
      <h1>URL Shortener</h1>
      <form>
        <h2>Paste the URL to be shortened</h2>
        <p className={"message"}>{message}</p>
        <input value={url} onChange={(e) => setUrl(e.target.value)}></input>
        <button onClick={(e) => submitHandler(e)}>Shorten URL</button>
        <p>http(s)://도 포함하여 입력해주세요.</p>
        {shortUrl !== "" && (
          <>
            <a href={shortUrl}>
              {shortUrl}
            </a>
            <span style={{ width: "10px", display: "inline-block" }} />
          </>
        )}
      </form>
    </section>
  );
}
