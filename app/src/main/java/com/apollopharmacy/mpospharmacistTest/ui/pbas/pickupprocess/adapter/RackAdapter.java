package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.apollopharmacy.mpospharmacistTest.R;

import com.apollopharmacy.mpospharmacistTest.databinding.AdapterRackBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RackAdapter extends RecyclerView.Adapter<RackAdapter.ViewHolder> {
    private Context context;
    private List<RacksDataResponse.FullfillmentDetail.Product> rackDataFilteredList;
    private PickupProcessMvpView pickupProcessMvpView;
    List<RacksDataResponse.FullfillmentDetail> dataResponse;
    List<List<RackBoxModel.ProductData>> listOfList;
    private boolean firstAccessCheck;

    public RackAdapter(Context context, List<RacksDataResponse.FullfillmentDetail.Product> rackDataFilteredList, List<RacksDataResponse.FullfillmentDetail> dataResponse, PickupProcessMvpView pickupProcessMvpView, List<List<RackBoxModel.ProductData>> listOfList, boolean acessCheck) {
        this.context = context;
        this.rackDataFilteredList = rackDataFilteredList;
        this.pickupProcessMvpView = pickupProcessMvpView;
        this.dataResponse = dataResponse;
        this.listOfList = listOfList;
        this.firstAccessCheck = acessCheck;
    }

    @NonNull
    @Override
    public RackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterRackBinding rackBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_rack, parent, false);
        return new RackAdapter.ViewHolder(rackBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull RackAdapter.ViewHolder holder, int position) {
        RacksDataResponse.FullfillmentDetail.Product rackModel = rackDataFilteredList.get(position);
        holder.rackBinding.rackId.setText(rackModel.getRackId());
        switch (rackModel.getExpandStatus()) {
            case 0:
                holder.rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));

//                holder.rackBinding.rackChildLayout.setBackgroundColor(context.getResources().getColor(R.color.lite_grey));
                holder.rackBinding.start.setVisibility(View.VISIBLE);
//                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.statusLayout.setVisibility(View.GONE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.GONE);
                holder.rackBinding.rackChild2Layout.setBackground(null);
                break;
            case 1:
                holder.rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_yellow_bg));
                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.status.setText("In progress");
                holder.rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.in_progress));
                holder.rackBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setBackground(context.getResources().getDrawable(R.drawable.yellow_stroke_bg));
                break;
            case 2:
                holder.rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_yellow_bg));
                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.status.setText("In progress");
                holder.rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.in_progress));
                holder.rackBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.GONE);
                holder.rackBinding.rackChild2Layout.setBackground(context.getResources().getDrawable(R.drawable.yellow_stroke_bg));
                break;
            case 3:
                holder.rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.status.setText("Partial");
                holder.rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
                holder.rackBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.GONE);
                holder.rackBinding.rackChild2Layout.setBackground(null);
                break;

            case 4:
                holder.rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.status.setText("Partial");
                holder.rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
                holder.rackBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setBackground(null);
                break;
            case 5:
                holder.rackBinding.rackChildLayout.setBackgroundColor(context.getResources().getColor(R.color.trans_red));
                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.status.setText("Not Available");
                holder.rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
                holder.rackBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.GONE);
                holder.rackBinding.rackChild2Layout.setBackground(null);
                break;
            case 6:
                holder.rackBinding.rackChildLayout.setBackgroundColor(context.getResources().getColor(R.color.trans_red));
                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.status.setText("Not Available");
                holder.rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
                holder.rackBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setBackground(null);
                break;
            case 7:
                holder.rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_green));
                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.status.setText("Full");
                holder.rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
                holder.rackBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.GONE);
                holder.rackBinding.rackChild2Layout.setBackground(null);
                break;
            case 8:
                holder.rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_green));
                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.status.setText("Full");
                holder.rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
                holder.rackBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setBackground(null);
                break;
            case 9:
                holder.rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_green));
                holder.rackBinding.start.setVisibility(View.GONE);
                holder.rackBinding.status.setText("Full");
                holder.rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
                holder.rackBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.rackBinding.rackChild2Layout.setVisibility(View.GONE);
                holder.rackBinding.rackChild2Layout.setBackground(null);
                break;
            default:
        }

        int flag = 0;
        List<RackBoxModel> boxStringList = new ArrayList<>();
        List<RackBoxModel.ProductData> productDataList = new ArrayList<>();
        for (int j = 0; j < dataResponse.size(); j++) {
            for (int k = 0; k < dataResponse.get(j).getProducts().size(); k++) {
                if (rackModel.getRackId().equalsIgnoreCase(dataResponse.get(j).getProducts().get(k).getRackId())) {
                    RackBoxModel rackBoxModel = new RackBoxModel();
                    rackBoxModel.setProductsCuont(flag + 1);
                    rackBoxModel.setRackBoxId(dataResponse.get(j).getBoxId());
                    RackBoxModel.ProductData productData = new RackBoxModel.ProductData();
                    productData.setProductId(dataResponse.get(j).getProducts().get(k).getProductId());
                    productData.setProductName(dataResponse.get(j).getProducts().get(k).getProductName());
                    productData.setRackId(dataResponse.get(j).getProducts().get(k).getRackId());
                    productData.setBoxId(dataResponse.get(j).getBoxId());
                    productData.setAvailableQuantity(dataResponse.get(j).getProducts().get(k).getAvailableQuantity());
                    productData.setRequiredQuantity(dataResponse.get(j).getProducts().get(k).getRequiredQuantity());
                    productData.setBatchId(dataResponse.get(j).getProducts().get(k).getBatchId());
                    if (listOfList != null && listOfList.size() > 0) {
                        for (int i = 0; i < listOfList.size(); i++) {
                            for (int l = 0; l < listOfList.get(i).size(); l++) {
                                if (listOfList.get(i).get(l).getProductId().equalsIgnoreCase(dataResponse.get(j).getProducts().get(k).getProductId())) {
                                    productData.setCapturedQuantity(listOfList.get(i).get(l).getCapturedQuantity());
                                    productData.setStatus(listOfList.get(i).get(l).getStatus());
                                    productData.setProductStatusFillingUpdate(listOfList.get(i).get(l).isProductStatusFillingUpdate());
                                }
                            }
                        }
                    } else {
                        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
                            for (int i = 0; i < pickupProcessMvpView.productList().size(); i++) {
                                for (int l = 0; l < pickupProcessMvpView.productList().size(); l++) {
                                    if (pickupProcessMvpView.productList().get(i).get(l).getProductId().equalsIgnoreCase(dataResponse.get(j).getProducts().get(k).getProductId())) {
                                        productData.setCapturedQuantity(pickupProcessMvpView.productList().get(i).get(l).getCapturedQuantity());
                                        productData.setStatus(pickupProcessMvpView.productList().get(i).get(l).getStatus());
                                        productData.setProductStatusFillingUpdate(pickupProcessMvpView.productList().get(i).get(l).isProductStatusFillingUpdate());
                                    }
                                }
                            }
                        } else {
                            productData.setCapturedQuantity(dataResponse.get(j).getProducts().get(k).getCapturedQuantity());
                            productData.setStatus(dataResponse.get(j).getProducts().get(k).getStatus());
                            productData.setProductStatusFillingUpdate(dataResponse.get(j).getProducts().get(k).isProductStatusFillingUpdate());
                        }
                    }
                    productDataList.add(productData);

                    boxStringList.add(rackBoxModel);
                }
            }
        }

        for (int i = 0; i < boxStringList.size(); i++) {
            for (int j = 0; j < boxStringList.size(); j++) {
                if (i != j && boxStringList.get(i).getRackBoxId().equals(boxStringList.get(j).getRackBoxId())) {
                    boxStringList.get(i).setProductsCuont(boxStringList.get(i).getProductsCuont() + boxStringList.get(j).getProductsCuont());
                    boxStringList.remove(j);
                }
            }
        }

//        completedViewCheck(productDataList, position, holder.rackBinding);
//        multipleStatusCheck(productDataList, position);
        if (!firstAccessCheck)
            firstTimeMultipleStatusCheck(productDataList, position, holder.rackBinding);

        Collections.reverse(boxStringList);

        RackRowAdapter rackRowAdapter = new RackRowAdapter(context, boxStringList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        holder.rackBinding.rackRowRecycler.setLayoutManager(mLayoutManager);
        holder.rackBinding.rackRowRecycler.setAdapter(rackRowAdapter);


        ProductListAdapter productListAdapter = new ProductListAdapter(context, productDataList, pickupProcessMvpView, true, listOfList);
        new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        holder.rackBinding.productListRecycler.setLayoutManager(new LinearLayoutManager(context));
        holder.rackBinding.productListRecycler.setAdapter(productListAdapter);

        holder.rackBinding.rackChildLayout.setOnClickListener(v -> {

//            completedViewCheck(productDataList, position, holder.rackBinding);
            firstAccessCheck = true;
            if (rackDataFilteredList.get(position).getExpandStatus() == 0) {
                rackDataFilteredList.get(position).setExpandStatus(1);
                notifyDataSetChanged();
            } else if (rackDataFilteredList.get(position).getExpandStatus() == 2) {
                rackDataFilteredList.get(position).setExpandStatus(1);
                notifyDataSetChanged();
            } else if (rackDataFilteredList.get(position).getExpandStatus() == 1) {
                multipleStatusCheck(productDataList, position);
            } else if (rackDataFilteredList.get(position).getExpandStatus() == 3) {
                rackDataFilteredList.get(position).setExpandStatus(4);
                notifyDataSetChanged();
            } else if (rackDataFilteredList.get(position).getExpandStatus() == 4) {
                multipleStatusCheck(productDataList, position);
            } else if (rackDataFilteredList.get(position).getExpandStatus() == 5) {
                rackDataFilteredList.get(position).setExpandStatus(6);
                notifyDataSetChanged();
            } else if (rackDataFilteredList.get(position).getExpandStatus() == 6) {
                multipleStatusCheck(productDataList, position);
            } else if (rackDataFilteredList.get(position).getExpandStatus() == 7) {
                rackDataFilteredList.get(position).setExpandStatus(8);
                notifyDataSetChanged();
            } else if (rackDataFilteredList.get(position).getExpandStatus() == 8) {
//                rackDataFilteredList.get(position).setExpandStatus(9);
//                notifyDataSetChanged();
                multipleStatusCheck(productDataList, position);
            } else if (rackDataFilteredList.get(position).getExpandStatus() == 9) {
//                multipleStatusCheck(productDataList, position);
            }
        });

        holder.rackBinding.gotoNextRack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rackDataFilteredList.get(position).getExpandStatus() == 0) {
                    rackDataFilteredList.get(position).setExpandStatus(1);
                    goToNextCheck(position + 1, productDataList);
                    if (position + 1 >= rackDataFilteredList.size())
                        goToNextCheck(0, productDataList);
                    else
                        goToNextCheck(position + 1, productDataList);
                    notifyDataSetChanged();
                } else if (rackDataFilteredList.get(position).getExpandStatus() == 2) {
                    rackDataFilteredList.get(position).setExpandStatus(1);
                    if (position + 1 >= rackDataFilteredList.size())
                        goToNextCheck(0, productDataList);
                    else
                        goToNextCheck(position + 1, productDataList);
                    notifyDataSetChanged();
                } else if (rackDataFilteredList.get(position).getExpandStatus() == 1) {
                    multipleStatusCheck(productDataList, position);
                    if (position + 1 >= rackDataFilteredList.size())
                        goToNextCheck(0, productDataList);
                    else
                        goToNextCheck(position + 1, productDataList);
                } else if (rackDataFilteredList.get(position).getExpandStatus() == 3) {
                    rackDataFilteredList.get(position).setExpandStatus(4);
                    if (position + 1 >= rackDataFilteredList.size())
                        goToNextCheck(0, productDataList);
                    else
                        goToNextCheck(position + 1, productDataList);
                    notifyDataSetChanged();
                } else if (rackDataFilteredList.get(position).getExpandStatus() == 4) {
                    multipleStatusCheck(productDataList, position);
                    if (position + 1 >= rackDataFilteredList.size())
                        goToNextCheck(0, productDataList);
                    else
                        goToNextCheck(position + 1, productDataList);
                } else if (rackDataFilteredList.get(position).getExpandStatus() == 5) {
                    rackDataFilteredList.get(position).setExpandStatus(6);
                    if (position + 1 >= rackDataFilteredList.size())
                        goToNextCheck(0, productDataList);
                    else
                        goToNextCheck(position + 1, productDataList);
                    notifyDataSetChanged();
                } else if (rackDataFilteredList.get(position).getExpandStatus() == 6) {
                    multipleStatusCheck(productDataList, position);
                    if (position + 1 >= rackDataFilteredList.size())
                        goToNextCheck(0, productDataList);
                    else
                        goToNextCheck(position + 1, productDataList);
                } else if (rackDataFilteredList.get(position).getExpandStatus() == 7) {
                    rackDataFilteredList.get(position).setExpandStatus(8);
                    if (position + 1 >= rackDataFilteredList.size())
                        goToNextCheck(0, productDataList);
                    else
                        goToNextCheck(position + 1, productDataList);
                    notifyDataSetChanged();
                } else if (rackDataFilteredList.get(position).getExpandStatus() == 8) {
                    multipleStatusCheck(productDataList, position);
                    if (position + 1 >= rackDataFilteredList.size())
                        goToNextCheck(0, productDataList);
                    else
                        goToNextCheck(position + 1, productDataList);
                }
            }
        });
    }

    private void goToNextCheck(int position, List<RackBoxModel.ProductData> productDataList) {
        if (rackDataFilteredList.get(position).getExpandStatus() == 0) {
            rackDataFilteredList.get(position).setExpandStatus(1);
            notifyDataSetChanged();
        } else if (rackDataFilteredList.get(position).getExpandStatus() == 2) {
            rackDataFilteredList.get(position).setExpandStatus(1);
            notifyDataSetChanged();
        } else if (rackDataFilteredList.get(position).getExpandStatus() == 1) {
            multipleStatusCheckDummy(position);
        } else if (rackDataFilteredList.get(position).getExpandStatus() == 3) {
            rackDataFilteredList.get(position).setExpandStatus(4);
            notifyDataSetChanged();
        } else if (rackDataFilteredList.get(position).getExpandStatus() == 4) {
            multipleStatusCheckDummy(position);
        } else if (rackDataFilteredList.get(position).getExpandStatus() == 5) {
            rackDataFilteredList.get(position).setExpandStatus(6);
            notifyDataSetChanged();
        } else if (rackDataFilteredList.get(position).getExpandStatus() == 6) {
            multipleStatusCheckDummy(position);
        } else if (rackDataFilteredList.get(position).getExpandStatus() == 7) {
            rackDataFilteredList.get(position).setExpandStatus(8);
            notifyDataSetChanged();
        } else if (rackDataFilteredList.get(position).getExpandStatus() == 8) {
            multipleStatusCheckDummy(position);
        }
    }

    private void multipleStatusCheckDummy(int position) {
        boolean full = false;
        boolean partial = false;
        boolean notAvailable = false;
        int notFlag = 0;
        int fullFalg = 0;

        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
            for (int i = 0; i < pickupProcessMvpView.productList().size(); i++) {
                for (int k = 0; k < pickupProcessMvpView.productList().get(i).size(); k++) {
                    for (int j = 0; j < pickupProcessMvpView.productsNextPosReturn().size(); j++) {
                        if (pickupProcessMvpView.productList().get(i).get(k).getProductId().equalsIgnoreCase(pickupProcessMvpView.productsNextPosReturn().get(j).getProductId())) {
                            if (pickupProcessMvpView.productsNextPosReturn().get(j).getStatus() != null) {
                                if (pickupProcessMvpView.productsNextPosReturn().get(j).getStatus().equalsIgnoreCase(pickupProcessMvpView.productList().get(i).get(k).getStatus()))
                                    pickupProcessMvpView.productsNextPosReturn().get(j).setStatus(pickupProcessMvpView.productList().get(i).get(k).getStatus());
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < pickupProcessMvpView.productsNextPosReturn().size(); i++) {
            if (pickupProcessMvpView.productsNextPosReturn().get(i).getStatus() != null) {
                if (pickupProcessMvpView.productsNextPosReturn().get(i).getStatus().equalsIgnoreCase("Partial")) {
                    partial = true;
                    notAvailable = false;
                    full = false;
                } else if (pickupProcessMvpView.productsNextPosReturn().get(i).getStatus().equalsIgnoreCase("NotAvailable")) {
                    partial = true;
                    notFlag = notFlag + 1;
                    if (notFlag == pickupProcessMvpView.productsNextPosReturn().size()) {
                        notAvailable = true;
                        partial = false;
                        full = false;
                    }
                } else if (pickupProcessMvpView.productsNextPosReturn().get(i).getStatus().equalsIgnoreCase("Full")) {
                    partial = true;
                    fullFalg = fullFalg + 1;
                    if (fullFalg == pickupProcessMvpView.productsNextPosReturn().size()) {
                        full = true;
                        partial = false;
                        notAvailable = false;
                    }
                }
            }
        }

        if (partial) {
            rackDataFilteredList.get(position).setExpandStatus(3);
            notifyDataSetChanged();
        } else if (notAvailable) {
            rackDataFilteredList.get(position).setExpandStatus(5);
            notifyDataSetChanged();
        } else if (full) {
            rackDataFilteredList.get(position).setExpandStatus(7);
            notifyDataSetChanged();
        } else {
            if (rackDataFilteredList.get(position).getExpandStatus() == 9) {
                rackDataFilteredList.get(position).setExpandStatus(8);
            } else {
                rackDataFilteredList.get(position).setExpandStatus(2);
            }
            notifyDataSetChanged();
        }
    }

    private void multipleStatusCheck(List<RackBoxModel.ProductData> productDataList, int position) {
        boolean full = false;
        boolean partial = false;
        boolean notAvailable = false;
        int notFlag = 0;
        int fullFalg = 0;

        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
            for (int i = 0; i < pickupProcessMvpView.productList().size(); i++) {
                for (int k = 0; k < pickupProcessMvpView.productList().get(i).size(); k++) {
                    for (int j = 0; j < productDataList.size(); j++) {
                        if (pickupProcessMvpView.productList().get(i).get(k).getProductId().equalsIgnoreCase(productDataList.get(j).getProductId())) {
                            if (productDataList.get(j).getStatus() != null) {
                                if (productDataList.get(j).getStatus().equalsIgnoreCase(pickupProcessMvpView.productList().get(i).get(k).getStatus()))
                                    productDataList.get(j).setStatus(pickupProcessMvpView.productList().get(i).get(k).getStatus());
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < productDataList.size(); i++) {
            if (productDataList.get(i).getStatus() != null) {
                if (productDataList.get(i).getStatus().equalsIgnoreCase("Partial")) {
                    partial = true;
                    notAvailable = false;
                    full = false;
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("NotAvailable")) {
                    partial = true;
                    notFlag = notFlag + 1;
                    if (notFlag == productDataList.size()) {
                        notAvailable = true;
                        partial = false;
                        full = false;
                    }
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("Full")) {
                    partial = true;
                    fullFalg = fullFalg + 1;
                    if (fullFalg == productDataList.size()) {
                        full = true;
                        partial = false;
                        notAvailable = false;
                    }
                }
            }
        }

        if (partial) {
            rackDataFilteredList.get(position).setExpandStatus(3);
            notifyDataSetChanged();
        } else if (notAvailable) {
            rackDataFilteredList.get(position).setExpandStatus(5);
            notifyDataSetChanged();
        } else if (full) {
            rackDataFilteredList.get(position).setExpandStatus(7);
            notifyDataSetChanged();
        } else {
            if (rackDataFilteredList.get(position).getExpandStatus() == 9) {
                rackDataFilteredList.get(position).setExpandStatus(8);
            } else {
                rackDataFilteredList.get(position).setExpandStatus(2);
            }
            notifyDataSetChanged();
        }
    }

    private void firstTimeMultipleStatusCheck(List<RackBoxModel.ProductData> productDataList, int position, AdapterRackBinding rackBinding) {
        boolean full = false;
        boolean partial = false;
        boolean notAvailable = false;
        int notFlag = 0;
        int fullFalg = 0;

        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
            for (int i = 0; i < pickupProcessMvpView.productList().size(); i++) {
                for (int k = 0; k < pickupProcessMvpView.productList().get(i).size(); k++) {
                    for (int j = 0; j < productDataList.size(); j++) {
                        if (pickupProcessMvpView.productList().get(i).get(k).getProductId().equalsIgnoreCase(productDataList.get(j).getProductId())) {
                            if (productDataList.get(j).getStatus() != null) {
                                if (productDataList.get(j).getStatus().equalsIgnoreCase(pickupProcessMvpView.productList().get(i).get(k).getStatus()))
                                    productDataList.get(j).setStatus(pickupProcessMvpView.productList().get(i).get(k).getStatus());
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < productDataList.size(); i++) {
            if (productDataList.get(i).getStatus() != null) {
                if (productDataList.get(i).getStatus().equalsIgnoreCase("Partial")) {
                    partial = true;
                    notAvailable = false;
                    full = false;
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("NotAvailable")) {
                    partial = true;
                    notFlag = notFlag + 1;
                    if (notFlag == productDataList.size()) {
                        notAvailable = true;
                        partial = false;
                        full = false;
                    }
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("Full")) {
                    partial = true;
                    fullFalg = fullFalg + 1;
                    if (fullFalg == productDataList.size()) {
                        full = true;
                        partial = false;
                        notAvailable = false;
                    }
                }
            }
        }

        if (partial) {
            rackDataFilteredList.get(position).setExpandStatus(3);
            rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
            rackBinding.start.setVisibility(View.GONE);
            rackBinding.status.setText("Partial");
            rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
            rackBinding.statusLayout.setVisibility(View.VISIBLE);
            rackBinding.rackChild2Layout.setVisibility(View.GONE);
            rackBinding.rackChild2Layout.setBackground(null);
        } else if (notAvailable) {
            rackDataFilteredList.get(position).setExpandStatus(5);
            rackBinding.rackChildLayout.setBackgroundColor(context.getResources().getColor(R.color.trans_red));
            rackBinding.start.setVisibility(View.GONE);
            rackBinding.status.setText("Not Available");
            rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
            rackBinding.statusLayout.setVisibility(View.VISIBLE);
            rackBinding.rackChild2Layout.setVisibility(View.GONE);
            rackBinding.rackChild2Layout.setBackground(null);
        } else if (full) {
            rackDataFilteredList.get(position).setExpandStatus(7);
            rackBinding.rackChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_green));
            rackBinding.start.setVisibility(View.GONE);
            rackBinding.status.setText("Full");
            rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            rackBinding.statusLayout.setVisibility(View.VISIBLE);
            rackBinding.rackChild2Layout.setVisibility(View.GONE);
            rackBinding.rackChild2Layout.setBackground(null);
        }
    }


    @Override
    public int getItemCount() {
        return rackDataFilteredList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterRackBinding rackBinding;

        public ViewHolder(@NonNull AdapterRackBinding rackBinding) {
            super(rackBinding.getRoot());
            this.rackBinding = rackBinding;
        }
    }

    public static class RackBoxModel {
        private String rackBoxId;
        private int productsCuont;

        public int getProductsCuont() {
            return productsCuont;
        }

        public void setProductsCuont(int productsCuont) {
            this.productsCuont = productsCuont;
        }

        public String getRackBoxId() {
            return rackBoxId;
        }

        public void setRackBoxId(String rackBoxId) {
            this.rackBoxId = rackBoxId;
        }

        public static class ProductData {

            private String productId;
            private String productName;
            private String rackId;
            private String boxId;
            private String availableQuantity;
            private String requiredQuantity;
            private String capturedQuantity;
            private String batchId;
            private String itemStatus;
            private String status;

            private boolean productStatusFillingUpdate;

            private boolean finalStatusUpdate;

            private String packerStatus;

            public String getItemStatus() {
                return itemStatus;
            }

            public void setItemStatus(String itemStatus) {
                this.itemStatus = itemStatus;
            }

            public String getPackerStatus() {
                return packerStatus;
            }

            public void setPackerStatus(String packerStatus) {
                this.packerStatus = packerStatus;
            }

            public boolean isFinalStatusUpdate() {
                return finalStatusUpdate;
            }

            public void setFinalStatusUpdate(boolean finalStatusUpdate) {
                this.finalStatusUpdate = finalStatusUpdate;
            }

            public boolean isProductStatusFillingUpdate() {
                return productStatusFillingUpdate;
            }

            public void setProductStatusFillingUpdate(boolean productStatusFillingUpdate) {
                this.productStatusFillingUpdate = productStatusFillingUpdate;
            }

            public String getBoxId() {
                return boxId;
            }

            public void setBoxId(String boxId) {
                this.boxId = boxId;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getRackId() {
                return rackId;
            }

            public void setRackId(String rackId) {
                this.rackId = rackId;
            }

            public String getAvailableQuantity() {
                return availableQuantity;
            }

            public void setAvailableQuantity(String availableQuantity) {
                this.availableQuantity = availableQuantity;
            }

            public String getRequiredQuantity() {
                return requiredQuantity;
            }

            public void setRequiredQuantity(String requiredQuantity) {
                this.requiredQuantity = requiredQuantity;
            }

            public String getCapturedQuantity() {
                return capturedQuantity;
            }

            public void setCapturedQuantity(String capturedQuantity) {
                this.capturedQuantity = capturedQuantity;
            }

            public String getBatchId() {
                return batchId;
            }

            public void setBatchId(String batchId) {
                this.batchId = batchId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}