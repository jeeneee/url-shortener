import axios from "axios";
import React, {useCallback, useEffect, useRef} from "react";
import {useHistory, useParams} from "react-router";

export default function ShortUrl() {
  const { shortUrl } = useParams();
  const cors = useRef(false);
  const history = useHistory();
  const redirectUrlApi = useCallback((url) => {
        return axios.get(url).catch((e) => {
          if (e.response === undefined) cors.current = true;
          else history.push(`/`);
        });
      },
      [history]
  );
  useEffect(() => {
    const url = `https://api.urlshortener.shop/${shortUrl}`;
    redirectUrlApi(url).then((res) => {
      if (res || cors.current) window.location.href = url;
    });
  }, [shortUrl, redirectUrlApi, cors]);

  return <div>{shortUrl}</div>;
}