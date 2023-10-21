import React from "react";
import styled from "styled-components";



const Line_styled = styled.div`
  margin-top: 30px;
  margin-bottom: 30px;
  padding-left: 25px;
  width: 300px;
`;
const HorizonLine = ({ text }) => {
  return (
    <Line_styled>
      <div
        style={{
          width: "100%",
          textAlign: "center",
          borderBottom: "1px solid #aaa",
          lineHeight: "0.1em",
          margin: "10px 0 20px",
        }}
      >
        <span style={{ background: "#fff", padding: "0 10px" }}>{text}</span>
      </div>
    </Line_styled>
  );
};

export default HorizonLine;