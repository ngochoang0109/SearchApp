import Wrapper from "../components/Wrapper/Wrapper";
import MenuBar from './../components/MenuBar/MenuBar';
import Container from './../components/Container/Container';
import DocumentPage from './../page/DocumentPage/DocumentPage';
import { Route, Routes } from "react-router-dom";
import SearchPage from "../page/SearchPage/SearchPage";
import Management from "../page/Management/Management";

function App() {
  return (
    <Wrapper>
      <MenuBar></MenuBar>
      <Container>
        <Routes>
          <Route path="/management-document" element={<Management ></Management>}>
          </Route>
          <Route path="/management-document" element={<Management ></Management>}>
          </Route>
          <Route path="/create-document" element={<DocumentPage ></DocumentPage>}>
          </Route>
          <Route path="/search-document" element={<SearchPage ></SearchPage>}>
          </Route>
        </Routes>
      </Container>
    </Wrapper>
  );
}

export default App;
