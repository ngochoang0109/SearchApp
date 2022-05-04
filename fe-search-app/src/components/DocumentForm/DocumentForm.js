import { useState } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { documentAction } from "../../action/documentAction";

const DocumentForm = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    const[onChange,setOnChange]=useState(false);

    const [expand, setExpand] = useState(1);

    const [index, setIndex] = useState({
        index: ''
    })

    const [data, setData] = useState({
        id: '',
        title: '',
        content: ''
    })

    const [dataSource, setDataSource] = useState([])

    const handlerChange = (event) => {
        setOnChange(true);
        const name = event.target.name;
        let value = event.target.value;

        if(name==='id'){
            value=parseInt(value)
        }

        if (name === 'index') {
            setIndex({
                index: value
            });
        } else {
            setData({
                ...data,
                [name]: value
            })
        }
    }

    const showFormDataSource = () => {
        const arr = new Array(expand).fill(0);
        const result = arr.map((item, index) => {
            return <div className="data-source" key={index}>
                <hr className="mt-2 mb-3" />
                <div className="form-group">
                    <label>ID</label>
                    <input name="id" className="form-control" onChange={handlerChange}></input>
                </div>
                <div className="form-group">
                    <label>Title</label>
                    <input name="title" className="form-control" onChange={handlerChange}></input>
                </div>
                <div className="form-group">
                    <label>Content</label> <br></br>
                    <textarea name="content" className="form-control" onChange={handlerChange}></textarea>
                </div>


                <hr className="mt-2 mb-3" />
            </div>


        })
        return result;
    }

    const handlerExpandDocument = () => {
        setExpand((preExpand) => {
            return preExpand + 1;
        })
        setDataSource([...dataSource, data]);
        setOnChange(false);
    }

    const handlerSubmit = (event) => {
        event.preventDefault();
        if(onChange){
            const source=dataSource;
            source.push(data);
        }
        dispatch(documentAction.addDocument({index:index.index,dataSource:dataSource}));
        navigate('/management-document',{replace:true});
    }


    return (
        <div className="card-body">
            <form method="post" className="form" onSubmit={handlerSubmit}>
                <div className="form-group">
                    <label>Index</label>
                    <input name="index" className="form-control" onChange={handlerChange}></input>
                </div>

                <div className="data-source">
                    {showFormDataSource()}

                </div>

                <input type="submit" value="Tạo/Update" className="btn btn-danger"></input>
            </form>
            <hr className="mt-2 mb-3" />
            <button className="btn btn-danger" onClick={handlerExpandDocument}>Thêm document</button>
        </div>
    )
}

export default DocumentForm;